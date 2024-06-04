package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.ScheduleValue;

public record AnniversaryValue(
        Long id,
        String name,
        ScheduleValue schedule
) {
    public static AnniversaryValue of(Anniversary anniversary, Schedule schedule) {
        return new AnniversaryValue(
                anniversary.getId(),
                anniversary.getName(),
                ScheduleValue.of(schedule)
        );
    }
}
