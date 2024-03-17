package com.cofixer.mf.mfcontentapi.dto.res;


import java.util.List;

public record GetMemberCalendarRes(

        List<MissionSummaryValue> memberCalendar
) {
    public static GetMemberCalendarRes of(List<MissionSummaryValue> memberCalendar) {
        return new GetMemberCalendarRes(memberCalendar);
    }
}
