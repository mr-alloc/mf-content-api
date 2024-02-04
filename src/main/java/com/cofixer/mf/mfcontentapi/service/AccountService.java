package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import com.cofixer.mf.mfcontentapi.domain.Account;
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
import com.cofixer.mf.mfcontentapi.validator.AccountValidator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountValidator accountValidator;
    private final AccountManager accountManager;
    private final MemberManager memberManager;
    private final CredentialManager credentialManager;
    private final EntityPrinter printer;
    private final ExpandableEntitySetting es;

    @Transactional
    public Account createAccount(CreateAccountReq req) {
        String originPassword = req.getPassword();
        Account newer = Account.forCreate(req.getEmail(), originPassword);

        //입력값 검증
        accountValidator.validate(newer.getEmail(), newer.getPassword());

        //이메일 중복 확인
        if (accountManager.isExistAccount(newer.getEmail())) {
            throw new AccountException(DeclaredAccountResult.DUPLICATED_EMAIL);
        }

        newer.changeEncrypted(EncryptUtil.encrypt(originPassword, EncryptAlgorithm.SHA256));

        //계정 생성
        Account saved = accountManager.createAccount(newer);
        memberManager.createMember(saved.getId());
        return saved;
    }

    @Transactional
    public VerifiedAccountRes verifyAccount(VerifyAccountReq req) {
        //입력값 검증
        accountValidator.validate(req.getEmail(), req.getPassword());

        //계정 조회
        Account found = accountManager.getExistedAccount(req.getEmail(),
                () -> new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT));
        String encrypted = EncryptUtil.encrypt(req.getPassword(), EncryptAlgorithm.SHA256);
        //비밀번호 확인
        if (!found.getPassword().equals(encrypted)) {
            throw new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT);
        }

        Instant instant = AppContext.APP_CLOCK.instant();
        String credential = JwtUtil.createToken(found.getId(), instant);
        log.info(printer.drawEntity(found, es.getConfig()));
        //토큰 등록
        credentialManager.registerCredential(found.getId(), credential);

        return new VerifiedAccountRes(credential);
    }

    @Transactional(readOnly = true)
    public AccountInfoRes getAccountInfo(Long aid) {
        Account account = accountManager.getExistedAccount(aid);
        return new AccountInfoRes(account.getEmail(), account.getPhone(), account.getCreatedAt());
    }
}
