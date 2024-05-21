package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;

public record ChangeFamilyMissionRes(

        Integer type,
        Long assignee,
        String title,
        Integer status
) {
    public static ChangeFamilyMissionRes of(ExpandedFamilyMission mission) {
        return new ChangeFamilyMissionRes(
                mission.getMissionType(),
                mission.getAssigneeId(),
                mission.getName(),
                mission.getStatus()
        );
    }
}
