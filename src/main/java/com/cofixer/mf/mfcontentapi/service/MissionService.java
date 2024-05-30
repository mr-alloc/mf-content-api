package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.domain.*;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.*;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.MissionManager;
import com.cofixer.mf.mfcontentapi.util.ConditionUtil;
import com.cofixer.mf.mfcontentapi.util.DateTimeUtil;
import com.cofixer.mf.mfcontentapi.util.ObjectUtil;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionManager missionManager;
    private final HolidayManager holidayManager;
    private final FamilyManager familyManager;

    @Transactional
    public CreateMissionRes createMission(CreateMissionReq req, AuthorizedMember authorizedMember) {
        Mission mission = Mission.forCreate(req, authorizedMember.getMemberId(), TemporalUtil.getEpochSecond());
        ExpandedMission expandedMission = ExpandedMission.forCreate(req);
        ExpandedMission newMission = missionManager.saveExpandedMission(mission, expandedMission);
        return CreateMissionRes.of(newMission);
    }

    @Transactional(readOnly = true)
    public GetMemberCalendarRes getMemberCalendar(Long mid, String startDate, String endDate) {
        long startTime = TemporalUtil.dateTimeToTimeStamp(startDate, LocalTime.MIN);
        long endTime = TemporalUtil.dateTimeToTimeStamp(endDate, LocalTime.MAX);

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

    @Transactional
    public CreateFamilyMissionRes createFamilyMission(CreateFamilyMissionReq req, AuthorizedMember authorizedMember) {
        FamilyMember assignee = familyManager.getFamilyMember(FamilyMemberId.of(authorizedMember.getFamilyId(), req.getAssignee()));
        Mission mission = Mission.forCreate(req, authorizedMember.getMemberId(), TemporalUtil.getEpochSecond());
        ExpandedFamilyMission expandedFamilyMission = ExpandedFamilyMission.forCreate(req, assignee, authorizedMember);

        ExpandedFamilyMission newerMission = missionManager.saveFamilyMission(mission, expandedFamilyMission);
        return CreateFamilyMissionRes.of(newerMission);
    }

    @Transactional(readOnly = true)
    public MissionDetailValue getMissionDetail(Long memberId, Long missionId) {
        ExpandedMission mission = missionManager.getIndividualMission(missionId);
        return MissionDetailValue.of(mission);
    }

    @Transactional(readOnly = true)
    public FamilyMissionDetailValue getFamilyMissionDetail(AuthorizedMember authorizedMember, Long missionId) {
        ExpandedFamilyMission expandedFamilyMission = missionManager.getFamilyMission(missionId, authorizedMember.getFamilyId());
        return FamilyMissionDetailValue.of(expandedFamilyMission);
    }

    @Transactional
    public ChangeFamilyMissionRes changeFamilyMission(AuthorizedMember authorizedMember, Long missionId, ChangeFamilyMissionReq request) {
        ExpandedFamilyMission mission = missionManager.getFamilyMission(missionId, authorizedMember.getFamilyId());
        //소속 패밀리 미션이 아닌경우
        ConditionUtil.throwIfTrue(Objects.equals(mission.getFamilyId(), authorizedMember.getFamilyId()), () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));
        // 변경할 내용이 없는 경우
        ConditionUtil.throwIfTrue(request.hasNotChanged(), () -> new MissionException(DeclaredMissionResult.NO_CHANGED_TARGET));

        LocalDateTime now = TemporalUtil.getNow();
        if (request.needChangeType()) {
            MissionType missionType = MissionType.fromValue(request.getType());
            mission.changeType(missionType, authorizedMember.getMemberId(), now);
        } else if (request.needChangeAssignee()) {
            //우리 패밀리에 없는 경우
            ConditionUtil.throwIfTrue(!familyManager.existMember(FamilyMemberId.of(authorizedMember.getFamilyId(), request.getAssignee())),
                    () -> new MissionException(DeclaredMissionResult.NOT_FOUND_MEMBER));
            mission.changeAssignee(request.getAssignee(), authorizedMember.getMemberId(), now);
        } else if (request.needChangeTitle()) {
            mission.changeTitle(request.getTitle(), authorizedMember.getMemberId(), now);
        } else if (request.needChangeStatus()) {
            mission.changeStatus(MissionStatus.fromCode(request.getStatus()), authorizedMember.getMemberId(), now);
        }
        return ChangeFamilyMissionRes.of(mission);
    }

    @Transactional
    public ChangeMissionRes changeMission(Long missionId, ChangeMissionReq req, AuthorizedMember authorizedMember) {
        ExpandedMission mission = missionManager.getIndividualMission(missionId);
        //나의 미션이 아닌경우
        ConditionUtil.throwIfTrue(ObjectUtil.notEquals(mission.getReporterId(), authorizedMember.getMemberId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));
        // 변경할 내용이 없는 경우
        ConditionUtil.throwIfTrue(req.hasNotChanged(), () -> new MissionException(DeclaredMissionResult.NO_CHANGED_TARGET));
        LocalDateTime now = TemporalUtil.getNow();
        if (req.needChangeType()) {
            MissionType missionType = MissionType.fromValue(req.getType());
            mission.changeType(missionType, now);
        } else if (req.needChangeTitle()) {
            mission.changeTitle(req.getTitle(), now);
        } else if (req.needChangeStatus()) {
            MissionStatus status = MissionStatus.fromCode(req.getStatus());
            ConditionUtil.throwIfTrue(status == MissionStatus.DELETED,
                    () -> new MissionException(DeclaredMissionResult.CANNOT_CHANGE_TO_DELETE));
            mission.changeStatus(status, now);
        } else if (req.needChangeDeadline()) {
            mission.changeDeadLine(req.getDeadline(), now);
        }

        return ChangeMissionRes.of(mission);
    }

    @Transactional
    public DeleteFamilyMissionRes deleteFamilyMission(Long missionId, AuthorizedMember authorizedMember) {
        ExpandedFamilyMission mission = missionManager.getFamilyMission(missionId, authorizedMember.getFamilyId());
        //소속 패밀리 미션이 아닌경우
        ConditionUtil.throwIfTrue(Objects.equals(mission.getFamilyId(), authorizedMember.getFamilyId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));

        //공개미션이 아니면서 미션 작성자가 아닌경우
        ConditionUtil.throwIfTrue(!mission.isPublic() && ObjectUtil.notEquals(mission.getReporterId(), authorizedMember.getMemberId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));

        mission.delete(authorizedMember.getMemberId());
        return DeleteFamilyMissionRes.of(missionId);
    }

    @Transactional
    public DeleteMissionRes deleteMission(Long missionId, AuthorizedMember authorizedMember) {
        ExpandedMission mission = missionManager.getIndividualMission(missionId);

        //나의 미션이 아닌경우
        ConditionUtil.throwIfTrue(ObjectUtil.notEquals(mission.getReporterId(), authorizedMember.getMemberId()),
                () -> new MissionException(DeclaredMissionResult.NOT_OWN_MISSION));

        mission.delete();
        return DeleteMissionRes.of(missionId);
    }
}
