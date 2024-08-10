package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.domain.UserSetting;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.res.GetUserSettingRes;
import com.cofixer.mf.mfcontentapi.manager.UserSettingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserSettingService {

    private final UserSettingManager manager;

    @Transactional(readOnly = true)
    public GetUserSettingRes getUserSetting(AuthorizedMember authorizedMember) {
        UserSetting userSetting = manager.getUserSetting(authorizedMember.getMemberId());
        return GetUserSettingRes.of(userSetting);
    }
}
