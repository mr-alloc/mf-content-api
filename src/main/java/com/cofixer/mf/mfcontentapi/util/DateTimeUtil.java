package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final Long MINUTE_IN_SECONDS = 60L;
    public static final Long HOUR_IN_SECONDS = 60L * MINUTE_IN_SECONDS;
    public static final Long DAY_IN_SECONDS = 24L * HOUR_IN_SECONDS;

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

}
