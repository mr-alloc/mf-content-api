package com.cofixer.mf.mfcontentapi.validator;

import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.repository.AccountRepository;
import com.cofixer.mf.mfcontentapi.util.EncryptUtil;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountValidator {

    public void validate(String email, String password) {
        //이메일 생성 규칙 확인
        if (ValidateUtil.nonValid(email, RegularExpression.EMAIL)) {
            throw new AccountException(DeclaredAccountResult.EMAIL_RULE_VIOLATION);
        }

        //비밀번호 생성 규칙 확인
        if (ValidateUtil.nonValid(password, RegularExpression.PASSWORD)) {
            throw new AccountException(DeclaredAccountResult.PASSWORD_RULE_VIOLATION);
        }
    }
}
