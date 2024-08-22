package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;

import java.util.List;

public interface ScheduleQueryRepository {

    List<Schedule> getSchedulesByRange(AuthorizedMember authorizedMember, Long startAt, Long endAt, ScheduleType scheduleType);

    List<Schedule> getComingSchedules(AuthorizedMember authorizedMember, ScheduleType scheduleType, Long timestamp);

    Schedule getScheduleByMissionId(Long missionId);

    List<Schedule> getAllSchedules(AuthorizedMember authorizedMember, ScheduleType scheduleType);
}
