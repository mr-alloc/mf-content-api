package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;

public record ChangeMissionRes(

        Integer type,
        String title,
        Integer status,
        Long deadline
) {
    public static ChangeMissionRes of(ExpandedMission mission) {
        return new ChangeMissionRes(
                mission.getMissionType(),
                mission.getName(),
                mission.getStatus(),
                mission.getDeadline()
        );
    }
}
