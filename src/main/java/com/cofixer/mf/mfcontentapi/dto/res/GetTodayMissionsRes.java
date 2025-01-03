package com.cofixer.mf.mfcontentapi.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "다가오는 미션 조회 정보")
public record GetTodayMissionsRes(
        @Schema(description = "미션 목록")
        List<MissionDetailValue> missions
) {
    public static GetTodayMissionsRes of(List<MissionDetailValue> details) {
        return new GetTodayMissionsRes(details);
    }
}
