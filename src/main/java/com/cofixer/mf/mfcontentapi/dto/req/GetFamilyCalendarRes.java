package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.res.FamilyMissionValue;
import com.cofixer.mf.mfcontentapi.dto.res.HolidayValue;

import java.util.List;

public record GetFamilyCalendarRes(
        List<FamilyMissionValue> calendar,

        List<HolidayValue> holidays
) {
    public static GetFamilyCalendarRes of(
            List<FamilyMissionValue> memberCalendar,
            List<HolidayValue> holidays
    ) {
        return new GetFamilyCalendarRes(memberCalendar, holidays);
    }
}
