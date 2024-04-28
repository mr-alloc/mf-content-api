package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;

public record FamilyMissionValue(
        Long id,
        String name,
        Long startStamp,
        Long endStamp,
        Long createdAt
) {

    public static FamilyMissionValue of(FamilyMission mission) {
        return new FamilyMissionValue(
                mission.getId(),
                mission.getName(),
                mission.getStartStamp(),
                mission.getEndStamp(),
                mission.getCreatedAt()
        );
    }
}
