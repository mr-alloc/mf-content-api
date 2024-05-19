package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;

public record FamilyMissionValue(
        Long id,
        Integer status,
        String name,
        Long startStamp,
        Long endStamp,

        Long startDate
) {

    public static FamilyMissionValue of(FamilyMission mission) {
        return new FamilyMissionValue(
                mission.getId(),
                mission.getStatus(),
                mission.getName(),
                mission.getStartStamp(),
                mission.getEndStamp(),
                mission.getStartDueDate()
        );
    }
}
