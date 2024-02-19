package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.dto.req.ConfirmAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.VerifyAccountReq;
import com.cofixer.mf.mfcontentapi.dto.res.AccountInfoRes;
import com.cofixer.mf.mfcontentapi.dto.res.VerifiedAccountRes;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.manager.AccountManager;
import com.cofixer.mf.mfcontentapi.manager.CredentialManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.util.EncryptUtil;
import com.cofixer.mf.mfcontentapi.util.JwtUtil;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final CommonValidator commonValidator;
    private final AccountManager accountManager;
    private final MemberManager memberManager;
    private final CredentialManager credentialManager;
    private final EntityPrinter printer;
    private final ExpandableEntitySetting es;

    @Transactional
    public Account createAccount(CreateAccountReq req) {
        //입력값 검증
        commonValidator.validateAccount(req.getEmail(), req.getPassword());

        //이메일 중복 확인
        if (accountManager.isExistAccount(req.getEmail())) {
            throw new AccountException(DeclaredAccountResult.DUPLICATED_EMAIL);
        }

        //계정 생성
        Account saved = accountManager.createAccount(
                Account.forCreate(
                        req.getEmail(),
                        EncryptUtil.encrypt(req.getPassword(), EncryptAlgorithm.SHA256)
                )
        );
        memberManager.createMember(saved.getId());
        return saved;
    }

    @Transactional
    public VerifiedAccountRes verifyAccount(VerifyAccountReq req) {
        //입력값 검증
        commonValidator.validateAccount(req.getEmail(), req.getPassword());

        //계정 조회
        Account found = accountManager.getExistedAccount(req.getEmail(),
                () -> new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT));
        String encrypted = EncryptUtil.encrypt(req.getPassword(), EncryptAlgorithm.SHA256);
        //비밀번호 확인
        if (!found.getPassword().equals(encrypted)) {
            throw new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT);
        }

        Instant instant = AppContext.APP_CLOCK.instant();
        log.info(printer.drawEntity(found, es.getConfig()));

        String accessToken = JwtUtil.createToken(found.getId(), instant);
        String refreshToken = UUID.randomUUID().toString();

        //토큰 등록
        credentialManager.registerCredential(found.getId(), refreshToken);

        return new VerifiedAccountRes(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public AccountInfoRes getAccountInfo(Long aid) {
        Account account = accountManager.getExistedAccount(aid);
        return new AccountInfoRes(account.getEmail(), account.getPhone(), account.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public void confirmAccount(ConfirmAccountReq req) {
        //생성규칙 확인
        commonValidator.validateAccount(req.getEmail());

        //계정 조회
        if (accountManager.isExistAccount(req.getEmail())) {
            throw new AccountException(DeclaredAccountResult.DUPLICATED_EMAIL);
        }
    }
}
