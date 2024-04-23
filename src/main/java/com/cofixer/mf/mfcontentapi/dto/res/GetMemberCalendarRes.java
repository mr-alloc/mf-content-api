package com.cofixer.mf.mfcontentapi.dto.res;


import java.util.List;

public record GetMemberCalendarRes(

        List<IndividualMissionValue> calendar,

        List<HolidayValue> holidays
) {
    public static GetMemberCalendarRes of(
            List<IndividualMissionValue> memberCalendar,
            List<HolidayValue> holidays
    ) {
        return new GetMemberCalendarRes(memberCalendar, holidays);
    }
}
