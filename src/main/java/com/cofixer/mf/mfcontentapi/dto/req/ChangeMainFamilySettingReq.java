package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "메인패밀리 설정변경 요청")
public record ChangeMainFamilySettingReq(

        @Min(1)
        @Schema(description = "변경할 패밀리")
        Long familyId
) {
        public boolean hasFamilyId() {
                return familyId != null && familyId != 0;
        }

}
