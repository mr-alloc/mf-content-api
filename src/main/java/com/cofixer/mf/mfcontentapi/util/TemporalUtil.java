package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.AppContext;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TemporalUtil {

    public static long toTimeStamp(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }

    public static long toTimeStamp(LocalDate date) {
        return date.toEpochDay();
    }

    public static long dateToTimeStamp(String dateTime) {
        return toTimeStamp(DateTimeUtil.parseDateTime(dateTime));
    }

    public static long dateTimeToTimeStamp(String date, LocalTime time) {
        return toTimeStamp(DateTimeUtil.parseDateTime(date, time));
    }

    public static long getEpochSecond() {
        return AppContext.APP_CLOCK.instant().getEpochSecond();
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.now(AppContext.APP_ZONE_OFFSET);
    }
}