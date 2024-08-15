package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.AppContext;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.cofixer.mf.mfcontentapi.AppContext.APP_ZONE_OFFSET_KST;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TemporalUtil {

    public static final Long MINUTE_IN_SECONDS = 60L;
    public static final Long HOUR_IN_SECONDS = 60L * MINUTE_IN_SECONDS;
    public static final Long DAY_IN_SECONDS = 24L * HOUR_IN_SECONDS;
    public static final Long WEEK_IN_SECONDS = 7 * DAY_IN_SECONDS;

    public static final Long DAYS30_IN_SECONDS = 30 * DAY_IN_SECONDS;

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

    public static LocalDateTime toLocalDateTime(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, APP_ZONE_OFFSET_KST);
    }
}
