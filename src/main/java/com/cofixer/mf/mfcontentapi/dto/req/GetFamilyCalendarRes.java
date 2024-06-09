package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.res.FamilyMissionDetailValue;
import com.cofixer.mf.mfcontentapi.dto.res.HolidayValue;

import java.util.List;

public record GetFamilyCalendarRes(
        List<FamilyMissionDetailValue> calendar,

        List<HolidayValue> holidays
) {
    public static GetFamilyCalendarRes of(
            List<FamilyMissionDetailValue> memberCalendar,
            List<HolidayValue> holidays
    ) {
        return new GetFamilyCalendarRes(memberCalendar, holidays);
    }
}
