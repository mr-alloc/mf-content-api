package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;

public record MissionValue(
        Long id,
        String name
) {

    public static MissionValue of(IndividualMission mission) {
        return new MissionValue(
                mission.getId(),
                mission.getName()
        );
    }
}
