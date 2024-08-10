package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.manager.ScheduleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleManager scheduleManager;


    public Map<Long, Schedule> getScheduleMap(AuthorizedMember authorizedMember, Long startAt, Long endAt, ScheduleType scheduleType) {
        return scheduleManager.getSchedules(authorizedMember, startAt, endAt, scheduleType).stream()
                .collect(Collectors.toMap(
                        Schedule::getScheduleId,
                        Function.identity()
                ));
    }
}
