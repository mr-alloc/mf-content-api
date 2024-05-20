package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;

public record FamilyMissionDetailValue(
        Long id,
        String name,
        Integer type,
        Integer status,
        Long assignee,
        Long reporter,
        Long remainSeconds,
        Long estimatedSeconds
) {

    public static FamilyMissionDetailValue of(FamilyMission mission) {
        return new FamilyMissionDetailValue(
                mission.getId(),
                mission.getName(),
                mission.getMissionType(),
                mission.getStatus(),
                mission.getAssigneeId(),
                mission.getReporterId(),
                mission.getRemainSeconds(),
                mission.getEstimatedSeconds()
        );
    }
}
