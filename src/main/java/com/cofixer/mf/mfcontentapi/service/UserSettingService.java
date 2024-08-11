package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;
import com.cofixer.mf.mfcontentapi.domain.UserSetting;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeMainFamilySettingReq;
import com.cofixer.mf.mfcontentapi.dto.res.ChangeMainFamilySettingRes;
import com.cofixer.mf.mfcontentapi.dto.res.GetUserSettingRes;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.UserSettingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSettingService {

    private final FamilyManager familyManager;
    private final UserSettingManager manager;

    @Transactional(readOnly = true)
    public GetUserSettingRes getUserSetting(AuthorizedMember authorizedMember) {
        UserSetting userSetting = manager.getUserSetting(authorizedMember.getMemberId());
        return GetUserSettingRes.of(userSetting);
    }

    @Transactional
    public ChangeMainFamilySettingRes changeMainFamily(
            AuthorizedMember authorizedMember,
            ChangeMainFamilySettingReq request
    ) {
        Long familyId = Optional.of(FamilyMemberId.of(request.familyId(), authorizedMember.getMemberId()))
                .filter(id -> request.hasFamilyId())
                .map(familyManager::getFamilyMember)
                .map(FamilyMember::getFamilyId)
                .orElse(0L);

        UserSetting userSetting = manager.getUserSetting(authorizedMember.getMemberId());
        userSetting.changeMainFamily(familyId);

        return ChangeMainFamilySettingRes.of(userSetting.getMainFamilyId());
    }
}
