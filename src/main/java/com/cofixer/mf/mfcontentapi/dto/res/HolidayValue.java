package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.constant.KoreanHoliday;

public record HolidayValue(

        Boolean isLunar,
        String date,
        String name
) {
    public static HolidayValue of(KoreanHoliday koreanHoliday) {
        return new HolidayValue(
                koreanHoliday.isLunar(),
                String.format("%02d-%02d", koreanHoliday.getMonth(), koreanHoliday.getDay()),
                koreanHoliday.getName()
        );
    }
}
