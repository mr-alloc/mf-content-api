package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.domain.FamilyMission;
import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.GetFamilyCalendarRes;
import com.cofixer.mf.mfcontentapi.dto.res.FamilyMissionValue;
import com.cofixer.mf.mfcontentapi.dto.res.GetMemberCalendarRes;
import com.cofixer.mf.mfcontentapi.dto.res.HolidayValue;
import com.cofixer.mf.mfcontentapi.dto.res.IndividualMissionValue;
import com.cofixer.mf.mfcontentapi.manager.MissionManager;
import com.cofixer.mf.mfcontentapi.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionManager missionManager;
    private final HolidayManager holidayManager;

    @Transactional
    public Mission createMission(CreateMissionReq req, AuthorizedMember authorizedMember) {
        if (authorizedMember.forFamilyMember()) {
            FamilyMission mission = FamilyMission.forCreate(req, authorizedMember);
            return missionManager.saveFamilyMission(mission);
        } else {
            IndividualMission mission = IndividualMission.forCreate(req, authorizedMember.getMemberId());
            return missionManager.saveIndividualMission(mission);
        }
    }

    @Transactional(readOnly = true)
    public GetMemberCalendarRes getMemberCalendar(Long mid, String startDate, String endDate) {
        long startTime = LocalDateTime.of(DateTimeUtil.parseDate(startDate), LocalTime.MIN).toEpochSecond(AppContext.APP_ZONE_OFFSET);
        long endTime = LocalDateTime.of(DateTimeUtil.parseDate(endDate), LocalTime.MAX).toEpochSecond(AppContext.APP_ZONE_OFFSET);

        List<IndividualMissionValue> missions = missionManager.getMissions(mid, startTime, endTime).stream()
                .map(IndividualMissionValue::of)
                .sorted(Comparator.comparing(IndividualMissionValue::deadLine))
                .toList();

        List<HolidayValue> holidays = holidayManager.getAllHolidays().stream()
                .map(HolidayValue::of)
                .toList();

        return GetMemberCalendarRes.of(missions, holidays);
    }

    @Transactional(readOnly = true)
    public GetFamilyCalendarRes getFamilyCalendar(AuthorizedMember authorizedMember, String startDate, String endDate) {
        long startTime = LocalDateTime.of(DateTimeUtil.parseDate(startDate), LocalTime.MIN).toEpochSecond(AppContext.APP_ZONE_OFFSET);
        long endTime = LocalDateTime.of(DateTimeUtil.parseDate(endDate), LocalTime.MAX).toEpochSecond(AppContext.APP_ZONE_OFFSET);

        List<FamilyMissionValue> missions = missionManager.getFamilyMissions(authorizedMember, startTime, endTime).stream()
                .map(FamilyMissionValue::of)
                .toList();

        List<HolidayValue> holidays = holidayManager.getAllHolidays().stream()
                .map(HolidayValue::of)
                .toList();
        return GetFamilyCalendarRes.of(missions, holidays);
    }
}
