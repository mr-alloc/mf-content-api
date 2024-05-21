package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;

public record FamilyMissionValue(
        Long id,
        Integer status,
        String name,
        Long startStamp,
        Long endStamp,

        Long startDate
) {

    public static FamilyMissionValue of(ExpandedFamilyMission mission) {
        return new FamilyMissionValue(
                mission.getId(),
                mission.getStatus(),
                mission.getName(),
                mission.getStartStamp(),
                mission.getEndStamp(),
                mission.getStartDueStamp()
        );
    }
}
