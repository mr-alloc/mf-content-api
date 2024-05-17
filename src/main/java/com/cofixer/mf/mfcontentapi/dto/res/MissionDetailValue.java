package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;

public record MissionDetailValue(
        Long id,
        String title,
        Integer type,
        Integer status,
        Long assignee,
        Long reporter
) {
    public static MissionDetailValue of(IndividualMission mission) {
        return new MissionDetailValue(
                mission.getId(),
                mission.getName(),
                mission.getMissionType(),
                mission.getStatus(),
                mission.getAssigneeId(),
                mission.getReporterId()
        );
    }
}
