package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.domain.MissionState;

public record MissionStateValue(
        Long missionId,
        Integer status,

        Long startAt,

        Long endAt
) {

    public static MissionStateValue of(MissionState state) {
        return new MissionStateValue(
                state.getMissionId(),
                state.getStatus(),
                state.getStartStamp(),
                state.getEndStamp()
        );
    }
}
