package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;

public record FamilyMissionValue(

        Long id,
        String name,
        Long createdAt
) {

    public static FamilyMissionValue of(FamilyMission mission) {
        return new FamilyMissionValue(
                mission.getId(),
                mission.getName(),
                mission.getCreatedAt()
        );
    }
}
