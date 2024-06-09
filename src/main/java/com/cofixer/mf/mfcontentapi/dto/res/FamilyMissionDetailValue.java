package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMissionDetail;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.MissionStateValue;
import com.cofixer.mf.mfcontentapi.dto.ScheduleValue;

import java.util.List;

public record FamilyMissionDetailValue(
        Long id,
        String name,
        Integer type,
        Long assignee,
        ScheduleValue schedule,
        List<MissionStateValue> states

) {

    public static FamilyMissionDetailValue of(FamilyMissionDetail detail, List<MissionStateValue> states, Schedule schedule) {
        Mission mission = detail.getMission();
        return new FamilyMissionDetailValue(
                detail.getMissionId(),
                mission.getName(),
                mission.getMissionType(),
                detail.getAssigneeId(),
                ScheduleValue.of(schedule),
                states
        );
    }

    public static FamilyMissionDetailValue of(FamilyMissionDetail detail, Mission mission, List<MissionStateValue> states, Schedule schedule) {
        return new FamilyMissionDetailValue(
                detail.getMissionId(),
                mission.getName(),
                mission.getMissionType(),
                detail.getAssigneeId(),
                ScheduleValue.of(schedule),
                states
        );
    }
}
