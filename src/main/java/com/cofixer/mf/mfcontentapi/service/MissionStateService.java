package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import com.cofixer.mf.mfcontentapi.domain.MissionState;
import com.cofixer.mf.mfcontentapi.dto.MissionStateValue;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.manager.MissionStateManager;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MissionStateService {

    private final MissionStateManager missionStateManager;

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

    public Map<Long, List<MissionStateValue>> getStateGroupingMap(List<Long> missionIdList) {
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
}
