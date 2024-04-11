package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.res.MissionSummaryValue;

import java.util.List;

public record GetFamilyCalendarRes(
        List<MissionSummaryValue> calendar
) {
    public static GetFamilyCalendarRes of(List<MissionSummaryValue> memberCalendar) {
        return new GetFamilyCalendarRes(memberCalendar);
    }
}
