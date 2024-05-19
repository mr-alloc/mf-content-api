package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;

public record ChangeMissionRes(

        Integer type,
        String title,
        Integer status,
        Long deadline
) {
    public static ChangeMissionRes of(IndividualMission mission) {
        return new ChangeMissionRes(
                mission.getMissionType(),
                mission.getName(),
                mission.getStatus(),
                mission.getDeadline()
        );
    }
}
