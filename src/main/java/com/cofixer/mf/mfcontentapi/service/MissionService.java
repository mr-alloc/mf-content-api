package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.dto.res.GetMemberCalendarRes;
import com.cofixer.mf.mfcontentapi.dto.res.MissionSummaryValue;
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

    public Mission createMission(CreateMissionReq req, Long memberId) {
        Mission mission = Mission.forCreate(req, memberId);
        return missionManager.saveMission(mission);
    }

    @Transactional(readOnly = true)
    public GetMemberCalendarRes getMemberCalendar(Long mid, String startDate, String endDate) {
        long startTime = LocalDateTime.of(DateTimeUtil.parseDate(startDate), LocalTime.MIN).toEpochSecond(AppContext.APP_ZONE_OFFSET);
        long endTime = LocalDateTime.of(DateTimeUtil.parseDate(endDate), LocalTime.MAX).toEpochSecond(AppContext.APP_ZONE_OFFSET);

        log.info("start: {} end: {}", startTime, endTime);
        List<MissionSummaryValue> missions = missionManager.getMissions(mid, startTime, endTime).stream()
                .map(MissionSummaryValue::of)
                .sorted(Comparator.comparing(MissionSummaryValue::deadLine))
                .toList();

        return GetMemberCalendarRes.of(missions);
    }
}
