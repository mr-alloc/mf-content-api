package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;

public record MissionValue(
        Long id,
        String name
) {

    public static MissionValue of(ExpandedMission mission) {
        return new MissionValue(
                mission.getId(),
                mission.getName()
        );
    }
}
