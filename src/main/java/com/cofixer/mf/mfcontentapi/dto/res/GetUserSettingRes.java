package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.UserSetting;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 설정정보 조회")
public record GetUserSettingRes(

        @Schema(description = "메인 패밀리")
        Long mainFamily
) {
    public static GetUserSettingRes of(UserSetting userSetting) {
        return new GetUserSettingRes(userSetting.getMainFamilyId());
    }
}
