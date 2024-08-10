package com.cofixer.mf.mfcontentapi.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "다가오는 패밀리 미션 조회 정보")
public record GetComingFamilyMissionsRes(

        @Schema(description = "미션 목록")
        List<FamilyMissionDetailValue> missions
) {


    public static GetComingFamilyMissionsRes of(List<FamilyMissionDetailValue> missions) {
        return new GetComingFamilyMissionsRes(missions);
    }
}
