package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String date, LocalTime localTime) {
        return LocalDateTime.of(parseDate(date), localTime);
    }

}
