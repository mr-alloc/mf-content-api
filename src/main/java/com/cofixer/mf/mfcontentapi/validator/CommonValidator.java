package com.cofixer.mf.mfcontentapi.validator;

import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.constant.DeclaredFamilyResult;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.exception.FamilyException;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommonValidator {

    public void validateEmail(String email) {
        //이메일 생성 규칙 확인
        if (ValidateUtil.nonValid(email, RegularExpression.EMAIL)) {
            throw new AccountException(DeclaredAccountResult.EMAIL_RULE_VIOLATION);
        }
    }

    public void validateAccount(String email, String password) {
        //이메일 생성 규칙 확인
        validateEmail(email);

        //비밀번호 생성 규칙 확인
        if (ValidateUtil.nonValid(password, RegularExpression.PASSWORD)) {
            throw new AccountException(DeclaredAccountResult.PASSWORD_RULE_VIOLATION);
        }
    }

    public void validateNickname(String nickname) {
        //닉네임 생성 규칙 확인
        if (ValidateUtil.nonValid(nickname, RegularExpression.NICKNAME)) {
            throw new MemberException(DeclaredMemberResult.NICKNAME_RULE_VIOLATION);
        }
    }

    public void validateFamily(Family family) {
        //패밀리 생성규칙 확인
        if (ValidateUtil.nonValid(family.getName(), RegularExpression.FAMILY_NAME)) {
            throw new FamilyException(DeclaredFamilyResult.FAMILY_NAME_RULE_VIOLATION);
        }
    }
}
