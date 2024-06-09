package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.domain.Schedule;

public record ScheduleValue(
        Long id,
        Integer mode,
        Long startAt,
        Long endAt,
        Integer repeatOption,
        Integer repeatValue
) {
    public static ScheduleValue of(Schedule schedule) {
        if (schedule == null) return null;

        return new ScheduleValue(
                schedule.getId(),
                schedule.getMode(),
                schedule.getStartAt(),
                schedule.getEndAt(),
                schedule.getRepeatOption(),
                schedule.getRepeatValue()
        );
    }
}
