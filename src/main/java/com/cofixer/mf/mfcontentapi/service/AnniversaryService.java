package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredValidateResult;
import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAnniversaryReq;
import com.cofixer.mf.mfcontentapi.dto.res.AnniversaryValue;
import com.cofixer.mf.mfcontentapi.exception.ValidateException;
import com.cofixer.mf.mfcontentapi.manager.AnniversaryManager;
import com.cofixer.mf.mfcontentapi.manager.ScheduleManager;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AnniversaryService {

    private final AnniversaryManager anniversaryManager;
    private final ScheduleManager scheduleManager;

    @Transactional(readOnly = true)
    public List<AnniversaryValue> getAnniversaries(Long startAt, Long endAt, AuthorizedMember authorizedMember) {
        if (!ValidateUtil.isValidStampRange(startAt, endAt)) {
            throw new ValidateException(DeclaredValidateResult.FAILED_AT_COMMON_VALIDATION);
        }

        Map<Long, Schedule> scheduleMap = CollectionUtil.toMap(
                scheduleManager.getSchedules(authorizedMember, startAt, endAt, ScheduleType.ANNIVERSARY),
                Schedule::getScheduleId
        );

        return anniversaryManager.getAnniversaries(scheduleMap.keySet()).stream()
                .filter(anniversary -> scheduleMap.containsKey(anniversary.getScheduleId()))
                .map(anniversary -> AnniversaryValue.of(anniversary, scheduleMap.get(anniversary.getScheduleId())))
                .toList();
    }

    @Transactional
    public List<AnniversaryValue> createAnniversaries(CreateAnniversaryReq req, AuthorizedMember authorizedMember) {
        List<Schedule> schedules = Schedule.forCreate(authorizedMember, req.scheduleInfo(), ScheduleType.ANNIVERSARY).stream()
                .map(scheduleManager::saveSchedule)
                .toList();

        List<Anniversary> anniversaries = schedules.stream()
                .map(schedule -> Anniversary.forCreate(req, schedule))
                .toList();

        Map<Long, Schedule> scheduleMap = CollectionUtil.toMap(schedules, Schedule::getScheduleId);
        return anniversaryManager.saveAnniversaries(anniversaries).stream()
                .map(anniversary -> AnniversaryValue.of(anniversary, scheduleMap.get(anniversary.getScheduleId())))
                .toList();
    }

}
