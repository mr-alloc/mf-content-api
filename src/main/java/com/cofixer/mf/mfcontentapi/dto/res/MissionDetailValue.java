package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.domain.MissionDetail;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.MissionStateValue;
import com.cofixer.mf.mfcontentapi.dto.ScheduleValue;

import java.util.List;

public record MissionDetailValue(
        Long id,
        String name,
        Integer type,
        Long deadline,
        ScheduleValue schedule,
        List<MissionStateValue> states
) {

    public static MissionDetailValue of(MissionDetail detail, Mission mission, List<MissionStateValue> states, Schedule schedule) {
        return new MissionDetailValue(
                detail.getMissionId(),
                mission.getName(),
                mission.getMissionType(),
                detail.getDeadline(),
                ScheduleValue.of(schedule),
                states
        );
    }

    public static MissionDetailValue of(MissionDetail detail, List<MissionStateValue> states, Schedule schedule) {
        Mission mission = detail.getMission();
        return new MissionDetailValue(
                detail.getMissionId(),
                mission.getName(),
                mission.getMissionType(),
                detail.getDeadline(),
                ScheduleValue.of(schedule),
                states
        );
    }

    public Long scheduleId() {
        return schedule.id();
    }
}
