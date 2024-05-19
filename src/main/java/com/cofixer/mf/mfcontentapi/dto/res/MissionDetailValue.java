package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;

public record MissionDetailValue(
        Long id,
        String name,
        Integer type,
        Integer status,
        Long reporter,
        Long deadline,
        Long remainSeconds
) {
    public static MissionDetailValue of(IndividualMission mission) {
        return new MissionDetailValue(
                mission.getId(),
                mission.getName(),
                mission.getMissionType(),
                mission.getStatus(),
                mission.getReporterId(),
                mission.getDeadline(),
                mission.getRemainSeconds()
        );
    }
}
