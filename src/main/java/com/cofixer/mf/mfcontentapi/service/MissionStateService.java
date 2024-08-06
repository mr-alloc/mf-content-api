package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.domain.*;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.MissionCommentValue;
import com.cofixer.mf.mfcontentapi.dto.MissionStateValue;
import com.cofixer.mf.mfcontentapi.dto.req.CreateCommentReq;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.manager.MissionCommentManager;
import com.cofixer.mf.mfcontentapi.manager.MissionManager;
import com.cofixer.mf.mfcontentapi.manager.MissionStateManager;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MissionStateService {

    private final MissionStateManager missionStateManager;
    private final MissionManager missionManager;
    private final MissionCommentManager missionCommentManager;
    private final ScheduleService scheduleService;

    public List<MissionStateValue> getStates(Long missionId) {
        return missionStateManager.getStates(missionId).stream()
                .map(MissionStateValue::of)
                .toList();
    }

    /**
     * Map<시작시간, MissionState> 가져오기
     *
     * @param missionId
     * @return
     */
    public Map<Long, MissionState> getStateMap(Long missionId) {
        return CollectionUtil.toMap(missionStateManager.getStates(missionId), MissionState::getStartStamp);
    }

    public MissionState getState(Long missionId, Long startStamp) {
        return missionStateManager.getState(missionId, startStamp);
    }

    public Map<Long, List<MissionStateValue>> getStateGroupingMap(Set<Long> missionIdList) {
        return missionStateManager.getStateAll(missionIdList).stream()
                .map(MissionStateValue::of)
                .collect(Collectors.groupingBy(
                        MissionStateValue::missionId
                ));
    }

    public MissionState getState(Long stateId) {
        return missionStateManager.getState(stateId)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_STATE));
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public MissionState createState(Mission mission, Schedule schedule) {
        MissionState toBeSaved = MissionState.forMissionCreate(
                mission.getId(),
                MissionType.fromValue(mission.getMissionType()),
                schedule
        );
        return missionStateManager.saveState(toBeSaved);
    }

    @Transactional
    public MissionCommentValue createComment(AuthorizedMember authorizedMember, Long stateId, CreateCommentReq req) {
        MissionState missionState = Optional.of(stateId).filter(id -> id > 0)
                .map(missionStateManager::getStateSafe)
                .orElseGet(() -> {
                    MissionDetail missionDetail = missionManager.getMissionDetail(req.missionId());
                    Mission mission = missionDetail.getMission();
                    if (!mission.getReporter().equals(authorizedMember.getMemberId())) {
                        throw new MissionException(DeclaredMissionResult.NOT_OWN_MISSION);
                    }
                    Schedule schedule = mission.getSchedule();
                    boolean isRange = schedule.getStartAt() <= req.timestamp() && req.timestamp() <= schedule.getEndAt();

                    return Optional.of(isRange)
                            .filter(isValidRange -> isValidRange)
                            .map(isValidRange -> missionStateManager.getState(mission.getId(), req.timestamp()))
                            .orElseGet(() -> missionStateManager.saveState(MissionState.forLazyCreate(
                                    req.missionId(),
                                    MissionType.fromValue(mission.getType()),
                                    schedule,
                                    req.timestamp()
                            )));
                });

        MissionComment saved = missionCommentManager.saveComment(MissionComment.forCreate(missionState, authorizedMember, req.content()));
        return MissionCommentValue.of(saved);
    }

    @Transactional(readOnly = true)
    public List<MissionCommentValue> getComments(AuthorizedMember authorizedMember, Long stateId) {
        if (Objects.isNull(stateId) || stateId <= 0) {
            return Collections.emptyList();
        }

        MissionState missionState = missionStateManager.getState(stateId)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_STATE));

        Mission mission = missionManager.getMission(missionState.getMissionId());
        Schedule schedule = mission.getSchedule();
        if (schedule.isNotAccessibleFrom(authorizedMember)) {
            return Collections.emptyList();
        }

        return missionCommentManager.getComments(missionState.getId()).stream()
                .sorted(MissionComment.DEFAULT_SORT_CONDITION)
                .map(MissionCommentValue::of)
                .toList();
    }

    public MissionState createStateLazy(Mission mission, Long startStamp) {
        MissionState missionState = MissionState.forLazyCreate(mission.getId(), MissionType.MISSION, mission.getSchedule(), startStamp);
        return missionStateManager.saveState(missionState);
    }
}
