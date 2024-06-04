package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.domain.Schedule;

public record ScheduleValue(
        Long startAt,
        Long endAt,
        Integer repeatOption,
        Long repeatValue
) {
    public static ScheduleValue of(Schedule schedule) {
        return new ScheduleValue(
                schedule.getStartAt(),
                schedule.getEndAt(),
                schedule.getRepeatOption(),
                schedule.getRepeatValue()
        );
    }
}
