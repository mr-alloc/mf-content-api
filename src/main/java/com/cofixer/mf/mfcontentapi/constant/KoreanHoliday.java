package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KoreanHoliday {
    NEW_YEAR(CalendarType.SOLAR, 1, 1, "신정"),
    MARCH_FIRST(CalendarType.SOLAR, 3, 1, "삼일절"),
    LABOR_DAY(CalendarType.SOLAR, 5, 1, "근로자의 날"),
    CHILDREN_DAY(CalendarType.SOLAR, 5, 5, "어린이날"),
    BUDDHA_BIRTHDAY(CalendarType.LUNAR, 4, 8, "석가탄신일"),
    MEMORIAL_DAY(CalendarType.SOLAR, 6, 6, "현충일"),
    INDEPENDENCE_DAY(CalendarType.SOLAR, 8, 15, "광복절"),
    NATIONAL_FOUNDATION_DAY(CalendarType.SOLAR, 10, 3, "개천절"),
    HANGEUL_DAY(CalendarType.SOLAR, 10, 9, "한글날"),
    CHRISTMAS(CalendarType.SOLAR, 12, 25, "크리스마스"),

    ;

    private final CalendarType calendarType;
    private final int month;
    private final int day;
    private final String name;

    public Boolean isLunar() {
        return CalendarType.LUNAR.equals(calendarType);
    }
}
