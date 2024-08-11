package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.*;
import com.cofixer.mf.mfcontentapi.domain.*;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.MissionStateValue;
import com.cofixer.mf.mfcontentapi.dto.req.*;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.MissionManager;
import com.cofixer.mf.mfcontentapi.manager.ScheduleManager;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import com.cofixer.mf.mfcontentapi.util.ConditionUtil;
import com.cofixer.mf.mfcontentapi.util.ObjectUtil;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionManager missionManager;
    private final HolidayManager holidayManager;
    private final FamilyManager familyManager;
    private final ScheduleManager scheduleManager;
    private final MissionStateService missionStateService;
    private final ScheduleService scheduleService;

    @Transactional
    public List<MissionDetailValue> createMission(CreateMissionReq req, AuthorizedMember authorizedMember) {
        List<Schedule> schedules = Schedule.forCreate(authorizedMember, req.scheduleInfo(), ScheduleType.MISSION).stream()
                .map(scheduleManager::saveSchedule)
                .toList();
        long now = TemporalUtil.getEpochSecond();
        return schedules.stream()
                .map(schedule -> {
                    Mission mission = Mission.forCreate(req, schedule, now);
                    MissionDetail missionDetail = missionManager.saveMissionDetail(mission);
                    processCreateState(mission, schedule);
                    return MissionDetailValue.of(missionDetail, mission, List.of(), schedule);
                })
                .toList();
    }

    private void processCreateState(Mission mission, Schedule schedule) {
        if (!ScheduleMode.REPEAT.equalsValue(schedule.getMode())) {
            missionStateService.createState(mission, schedule);
        }
    }


    @Transactional
    public List<FamilyMissionDetailValue> createFamilyMission(CreateFamilyMissionReq req, AuthorizedMember authorizedMember) {
        List<Schedule> schedules = Schedule.forCreate(authorizedMember, req.scheduleInfo(), ScheduleType.MISSION).stream()
                .map(scheduleManager::saveSchedule)
                .toList();
        long now = TemporalUtil.getEpochSecond();
        FamilyMember assignee = familyManager.getFamilyMember(
                FamilyMemberId.of(authorizedMember.getFamilyId(), req.getAssigneeSafe(authorizedMember.getMemberId()))
        );

        return schedules.stream()
                .map(schedule -> {
                    Mission mission = Mission.forCreate(req, schedule, now);
                    FamilyMissionDetail detail = missionManager.saveFamilyMissionDetail(mission, assignee, authorizedMember);
                    processCreateState(mission, schedule);
                    return FamilyMissionDetailValue.of(detail, mission, List.of(), schedule);
                })
                .toList();

    }

    @Transactional(readOnly = true)
    public GetMemberCalendarRes getMemberCalendar(AuthorizedMember authorizedMember, Long startAt, Long endAt) {
        List<Schedule> schedules = scheduleManager.getSchedules(authorizedMember, startAt, endAt, ScheduleType.MISSION);
        Set<Long> scheduleIds = CollectionUtil.convertSet(schedules, Schedule::getScheduleId);
        List<MissionDetail> details = missionManager.getMissionDetails(scheduleIds);

        Set<Long> missionIds = CollectionUtil.convertSet(details, MissionDetail::getMissionId);
        Map<Long, List<MissionStateValue>> stateGroupingMap = missionStateService.getStateGroupingMap(missionIds);

        Map<Long, Schedule> scheduleMap = scheduleService.getScheduleMap(authorizedMember, startAt, endAt, ScheduleType.MISSION);

        List<MissionDetailValue> detailValues = details.stream()
                .map(detail -> MissionDetailValue.of(
                        detail,
                        stateGroupingMap.get(detail.getMissionId()),
                        scheduleMap.get(detail.getScheduleId())
                ))
                .sorted(Comparator.comparing(MissionDetailValue::deadline))
                .toList();

        List<HolidayValue> holidays = holidayManager.getAllHolidays().stream()
                .map(HolidayValue::of)
                .toList();

        return GetMemberCalendarRes.of(detailValues, holidays);
    }

    @Transactional(readOnly = true)
    public GetFamilyCalendarRes getFamilyCalendar(AuthorizedMember authorizedMember, Long startAt, Long endAt) {
        List<Schedule> schedules = scheduleManager.getSchedules(authorizedMember, startAt, endAt, ScheduleType.MISSION);
        Set<Long> scheduleIds = CollectionUtil.convertSet(schedules, Schedule::getScheduleId);

        List<FamilyMissionDetail> details = missionManager.getFamilyMissionDetails(scheduleIds);
        Set<Long> missionIdList = CollectionUtil.convertSet(details, FamilyMissionDetail::getMissionId);

        Map<Long, List<MissionStateValue>> stateGroupingMap = missionStateService.getStateGroupingMap(missionIdList);
        Map<Long, Schedule> scheduleMap = scheduleService.getScheduleMap(authorizedMember, startAt, endAt, ScheduleType.MISSION);

        List<FamilyMissionDetailValue> detailValues = details.stream()
                .map(detail -> {
                    List<MissionStateValue> missionStates = stateGroupingMap.get(detail.getMissionId());
                    Schedule schedule = scheduleMap.get(detail.getMission().getScheduleId());
                    return FamilyMissionDetailValue.of(detail, missionStates, schedule);
                })
                .toList();

        List<HolidayValue> holidays = holidayManager.getAllHolidays().stream()
                .map(HolidayValue::of)
                .toList();

        return GetFamilyCalendarRes.of(detailValues, holidays);
    }

    @Transactional(readOnly = true)
    public MissionDetailValue getMissionDetail(AuthorizedMember authorizedMember, Long missionId) {
        Schedule schedule = scheduleManager.getScheduleByMissionId(missionId);
        if (schedule.isNotAccessibleFrom(authorizedMember)) {
            throw new MissionException(DeclaredMissionResult.NOT_OWN_MISSION);
        }

        MissionDetail detail = missionManager.getMissionDetail(missionId);
        List<MissionStateValue> states = missionStateService.getStates(missionId);
        return MissionDetailValue.of(detail, states, schedule);
    }

    @Transactional(readOnly = true)
    public FamilyMissionDetailValue getFamilyMissionDetail(AuthorizedMember authorizedMember, Long missionId) {
        Schedule schedule = scheduleManager.getScheduleByMissionId(missionId);
        if (schedule.isNotAccessibleFrom(authorizedMember)) {
            throw new MissionException(DeclaredMissionResult.NOT_OWN_MISSION);
        }
        FamilyMissionDetail detail = missionManager.getFamilyMissionDetail(missionId, authorizedMember.getFamilyId());
        List<MissionStateValue> states = missionStateService.getStates(missionId);
        return FamilyMissionDetailValue.of(detail, states, schedule);
    }

    @Transactional
    public ChangeFamilyMissionRes changeFamilyMission(AuthorizedMember authorizedMember, Long missionId, ChangeFamilyMissionReq request) {
        FamilyMissionDetail detail = missionManager.getFamilyMissionDetail(missionId, authorizedMember.getFamilyId());
        Mission mission = detail.getMission();
        //소속 패밀리 미션이 아닌경우
        ConditionUtil.throwIfTrue(!Objects.equals(mission.getSchedule().getFamily(), authorizedMember.getFamilyId()), () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));
        // 변경할 내용이 없는 경우
        ConditionUtil.throwIfTrue(request.hasNotChanged(), () -> new MissionException(DeclaredMissionResult.NO_CHANGED_TARGET));

        long now = TemporalUtil.getEpochSecond();
        if (request.needChangeType()) {
            MissionType missionType = MissionType.fromValue(request.getType());
            detail.changeType(missionType, authorizedMember.getMemberId(), now);
        }

        if (request.needChangeAssignee()) {
            //우리 패밀리에 없는 경우
            ConditionUtil.throwIfTrue(!familyManager.existMember(FamilyMemberId.of(authorizedMember.getFamilyId(), request.getAssignee())),
                    () -> new MissionException(DeclaredMissionResult.NOT_FOUND_MEMBER));
            detail.changeAssignee(request.getAssignee(), authorizedMember.getMemberId(), now);
        }

        if (request.needChangeTitle()) {
            detail.changeTitle(request.getTitle(), authorizedMember.getMemberId(), now);
        }

        if (request.needChangeStatus()) {
            MissionStatus status = MissionStatus.fromCode(request.getStatus());
            ConditionUtil.throwIfTrue(status == MissionStatus.DELETED,
                    () -> new MissionException(DeclaredMissionResult.CANNOT_CHANGE_TO_DELETE));
            detail.renewLastUpdateMember(authorizedMember.getMemberId());
            mission.renewUpdatedAt(now);
            MissionState state = Optional.ofNullable(request.getStateId())
                    .map(missionStateService::getState)
                    .orElseGet(() -> missionStateService.createStateLazy(mission, request.getStartStamp()));
            if (state.getStatus().equals(request.getStatus())) {
                throw new MissionException(DeclaredMissionResult.NO_CHANGED_TARGET);
            }
            state.changeStatus(status);
        }

        if (request.needChangeDeadline()) {
            mission.changeDeadLine(request.getDeadline(), now);
        }

        if (request.needChangeDescription()) {
            mission.changeDescription(request.getDescription(), now);
        }

        List<MissionStateValue> states = missionStateService.getStates(detail.getMissionId());
        Schedule schedule = scheduleManager.getSchedule(mission.getScheduleId());
        FamilyMissionDetailValue detailValue = FamilyMissionDetailValue.of(detail, states, schedule);
        return ChangeFamilyMissionRes.of(detailValue);
    }

    @Transactional
    public ChangeMissionRes changeMission(Long missionId, ChangeMissionReq request, AuthorizedMember authorizedMember) {
        MissionDetail detail = missionManager.getMissionDetail(missionId);
        Mission mission = detail.getMission();
        //나의 미션이 아닌경우
        ConditionUtil.throwIfTrue(ObjectUtil.notEquals(mission.getSchedule().getReporter(), authorizedMember.getMemberId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));
        // 변경할 내용이 없는 경우
        ConditionUtil.throwIfTrue(request.hasNotChanged(), () -> new MissionException(DeclaredMissionResult.NO_CHANGED_TARGET));
        long now = TemporalUtil.getEpochSecond();
        if (request.needChangeType()) {
            MissionType missionType = MissionType.fromValue(request.getType());
            mission.changeType(missionType, now);
        }

        if (request.needChangeTitle()) {
            mission.changeTitle(request.getTitle(), now);
        }

        if (request.needChangeStatus()) {
            MissionStatus status = MissionStatus.fromCode(request.getStatus());
            ConditionUtil.throwIfTrue(status == MissionStatus.DELETED,
                    () -> new MissionException(DeclaredMissionResult.CANNOT_CHANGE_TO_DELETE));
            mission.renewUpdatedAt(now);
            MissionState state = Optional.ofNullable(request.getStateId())
                    .map(missionStateService::getState)
                    .orElseGet(() -> missionStateService.createStateLazy(mission, request.getStartStamp()));
            if (state.getStatus().equals(request.getStatus())) {
                throw new MissionException(DeclaredMissionResult.NO_CHANGED_TARGET);
            }
            state.changeStatus(status);
        }

        if (request.needChangeDeadline()) {
            mission.changeDeadLine(request.getDeadline(), now);
        }


        if (request.needChangeDescription()) {
            mission.changeDescription(request.getDescription(), now);
        }

        List<MissionStateValue> states = missionStateService.getStates(detail.getMissionId());
        Schedule schedule = scheduleManager.getSchedule(mission.getScheduleId());
        MissionDetailValue detailValue = MissionDetailValue.of(detail, states, schedule);
        return ChangeMissionRes.of(detailValue);
    }

    @Transactional
    public DeleteFamilyMissionRes deleteFamilyMission(Long missionId, AuthorizedMember authorizedMember) {
        FamilyMissionDetail detail = missionManager.getFamilyMissionDetail(missionId, authorizedMember.getFamilyId());
        Mission mission = detail.getMission();
        //소속 패밀리 미션이 아닌경우
        ConditionUtil.throwIfTrue(Objects.equals(mission.getFamily(), authorizedMember.getFamilyId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));

        //공개미션이 아니면서 미션 작성자가 아닌경우
        ConditionUtil.throwIfTrue(!mission.isPublic() && ObjectUtil.notEquals(mission.getReporter(), authorizedMember.getMemberId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));

        detail.delete(authorizedMember.getMemberId());
        return DeleteFamilyMissionRes.of(missionId);
    }

    @Transactional
    public DeleteMissionRes deleteMission(Long missionId, AuthorizedMember authorizedMember) {
        MissionDetail detail = missionManager.getMissionDetail(missionId);
        Mission mission = detail.getMission();

        //나의 미션이 아닌경우
        ConditionUtil.throwIfTrue(ObjectUtil.notEquals(mission.getReporter(), authorizedMember.getMemberId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));

        mission.delete();
        return DeleteMissionRes.of(missionId);
    }

    @Transactional(readOnly = true)
    public GetComingFamilyMissionsRes getComingFamilyMissions(AuthorizedMember authorizedMember) {
        Map<Long, Schedule> schedules = scheduleManager.getComingMissionSchedules(authorizedMember, ScheduleType.MISSION).stream()
                .collect(Collectors.toMap(Schedule::getScheduleId, Function.identity()));
        Set<Long> scheduleIds = CollectionUtil.convertSet(schedules.values(), Schedule::getScheduleId);

        Map<Long, FamilyMissionDetail> detailsMap = missionManager.getFamilyMissionDetails(scheduleIds).stream()
                .filter(detail -> detail.isMyMission(authorizedMember))
                .collect(Collectors.toMap(
                        FamilyMissionDetail::getMissionId,
                        Function.identity()
                ));
        Set<Long> missionIds = CollectionUtil.convertSet(detailsMap.values(), FamilyMissionDetail::getMissionId);
        Map<Long, List<MissionStateValue>> stateMap = missionStateService.getStateGroupingMap(missionIds);

        List<FamilyMissionDetailValue> detailValues = missionManager.getMissions(missionIds).stream()
                .map(mission -> FamilyMissionDetailValue.of(
                        detailsMap.get(mission.getMissionId()),
                        mission,
                        stateMap.get(mission.getMissionId()),
                        schedules.get(mission.getScheduleId())
                ))
                .toList();

        return GetComingFamilyMissionsRes.of(detailValues);
    }

    public GetComingMissionsRes getComingMissions(AuthorizedMember authorizedMember) {
        Map<Long, Schedule> schedules = scheduleManager.getComingMissionSchedules(authorizedMember, ScheduleType.MISSION).stream()
                .collect(Collectors.toMap(Schedule::getScheduleId, Function.identity()));
        Set<Long> scheduleIds = CollectionUtil.convertSet(schedules.values(), Schedule::getScheduleId);

        Map<Long, MissionDetail> detailsMap = missionManager.getMissionDetailsMap(scheduleIds);
        Set<Long> missionIds = CollectionUtil.convertSet(detailsMap.values(), MissionDetail::getMissionId);
        Map<Long, List<MissionStateValue>> stateMap = missionStateService.getStateGroupingMap(missionIds);

        List<MissionDetailValue> detailValues = missionManager.getMissions(missionIds).stream()
                .map(mission -> MissionDetailValue.of(
                        detailsMap.get(mission.getMissionId()),
                        mission,
                        stateMap.get(mission.getMissionId()),
                        schedules.get(mission.getScheduleId())
                ))
                .toList();

        return GetComingMissionsRes.of(detailValues);

    }
}
