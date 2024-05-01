package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredFamilyResult;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.exception.FamilyException;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final CommonValidator commonValidator;
    private final FamilyManager familyManager;

    @Transactional
    public void changeNickname(AuthorizedMember authorizedMember, ChangeNicknameReq req) {
        commonValidator.validateNickname(req.getNickname());
        FamilyMember familyMember = familyManager.getFamilyMember(FamilyMemberId.of(authorizedMember));

        if (familyMember.hasNickName() && familyMember.nicknameIs(req.getNickname())) {
            throw new FamilyException(DeclaredFamilyResult.CANNOT_CHANGE_SAME_NICKNAME);
        }

        familyMember.changeNickname(req.getNickname());
    }

}
