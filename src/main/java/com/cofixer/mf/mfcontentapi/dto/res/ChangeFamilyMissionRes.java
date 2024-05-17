package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;

public record ChangeFamilyMissionRes(

        Long assignee,
        String title,
        Integer status
) {
    public static ChangeFamilyMissionRes of(FamilyMission mission) {
        return new ChangeFamilyMissionRes(
                mission.getAssigneeId(),
                mission.getName(),
                mission.getStatus()
        );
    }
}
