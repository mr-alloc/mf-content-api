package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.domain.Schedule;

import java.util.List;

public record ScheduleValue(
        Long id,
        Integer mode,
        Long startAt,
        Long scheduleTime,
        Long endAt,
        Integer repeatOption,
        List<Integer> repeatValues
) {
    public static ScheduleValue of(Schedule schedule) {
        if (schedule == null) return null;

        return new ScheduleValue(
                schedule.getId(),
                schedule.getMode(),
                schedule.getStartAt(),
                schedule.getScheduleTime(),
                schedule.getEndAt(),
                schedule.getRepeatOption(),
                schedule.getRepeatValues()
        );
    }
}
