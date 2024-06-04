package com.cofixer.mf.mfcontentapi.dto;

import java.util.List;

public record ScheduleInfo(

        Integer scheduleMode,
        List<Long> selected,

        Long startAt,
        Long endAt,

        Integer repeatOption,
        Long repeatValue
) {
}
