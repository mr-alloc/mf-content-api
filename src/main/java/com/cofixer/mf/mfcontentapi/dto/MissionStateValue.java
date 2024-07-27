package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.domain.MissionState;

public record MissionStateValue(
        Long id,

        Long missionId,
        Integer status,
        Long startAt,
        Long endAt,
        Long concreteStartAt,
        Long concreteCompleteAt
) {

    public static MissionStateValue of(MissionState state) {
        return new MissionStateValue(
                state.getId(),
                state.getMissionId(),
                state.getStatus(),
                state.getStartStamp(),
                state.getEndStamp(),
                state.getStartTime(),
                state.getCompleteTime()
        );
    }
}
