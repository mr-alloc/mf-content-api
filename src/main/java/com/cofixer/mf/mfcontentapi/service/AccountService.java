package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.VerifyAccountReq;
import com.cofixer.mf.mfcontentapi.dto.res.VerifiedAccountRes;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.manager.AccountManager;
import com.cofixer.mf.mfcontentapi.util.EncryptUtil;
import com.cofixer.mf.mfcontentapi.util.JwtUtil;
import com.cofixer.mf.mfcontentapi.validator.AccountValidator;
import kr.devis.util.entityprinter.print.PrintConfigurator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableSetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountValidator accountValidator;
    private final AccountManager accountManager;
    private final EntityPrinter printer;
    private final PrintConfigurator<Integer> ec;

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
        return accountManager.createAccount(newer);
    }

    @Transactional(readOnly = true)
    public VerifiedAccountRes verifyAccount(VerifyAccountReq req) {
        //입력값 검증
        accountValidator.validate(req.getEmail(), req.getPassword());

        //계정 조회
        Account found = accountManager.getAccount(req.getEmail(),
                () -> new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT));
        log.info(printer.drawEntity(found, ec));
        String encrypted = EncryptUtil.encrypt(req.getPassword(), EncryptAlgorithm.SHA256);
        //비밀번호 확인
        if ( ! found.getPassword().equals(encrypted)) {
            throw new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT);
        }
        String credential = JwtUtil.createToken(found.getId());

        return new VerifiedAccountRes(credential);
    }
}
