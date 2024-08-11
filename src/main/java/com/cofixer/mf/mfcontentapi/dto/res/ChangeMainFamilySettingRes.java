package com.cofixer.mf.mfcontentapi.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메인 패밀리 설정변경 결과")
public record ChangeMainFamilySettingRes(

        @Schema(description = "패밀리 아이디")
        Long changedFamily
) {
    public static ChangeMainFamilySettingRes of(Long mainFamilyId) {
        return new ChangeMainFamilySettingRes(mainFamilyId);
    }
}
