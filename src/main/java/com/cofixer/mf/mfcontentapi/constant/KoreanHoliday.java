package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KoreanHoliday {

    LABOR_DAY(1, CalendarType.SOLAR, 5, 1, "근로자의 날"),
    CHILDREN_DAY(2, CalendarType.SOLAR, 5, 5, "어린이날"),
    BUDDHA_BIRTHDAY(3, CalendarType.LUNAR, 4, 8, "석가탄신일"),
    MEMORIAL_DAY(4, CalendarType.SOLAR, 6, 6, "현충일"),

    ;

    private final int value;
    private final CalendarType calendarType;
    private final int month;
    private final int day;
    private final String name;
}
