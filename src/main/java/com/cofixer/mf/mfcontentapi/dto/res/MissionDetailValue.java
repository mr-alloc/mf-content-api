package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;

public record MissionDetailValue(
        Long id,
        String name,
        Integer type,
        Integer status,
        Long reporter,
        Long deadline,
        Long remainSeconds
) {
    public static MissionDetailValue of(ExpandedMission mission) {
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
