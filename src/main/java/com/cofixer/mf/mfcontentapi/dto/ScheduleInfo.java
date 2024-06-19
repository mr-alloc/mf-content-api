package com.cofixer.mf.mfcontentapi.dto;

import java.util.List;

public record ScheduleInfo(

        Integer scheduleMode,
        List<Long> selected,

        Long startAt,
        Long scheduleTime,
        Long endAt,

        Integer repeatOption,
        List<Integer> repeatValues
) {
    public Long getFirstSelected() {
        return selected.get(0);
    }

    public Integer getFirstRepeatValue() {
        return repeatValues.get(0);
    }
}
