package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionState;

import java.util.Collection;
import java.util.List;

public interface MissionStateQueryRepository {
    List<MissionState> getStateAllByMissionId(Long missionId);

    MissionState getState(Long missionId, Long startStamp);

    List<MissionState> getStateAllByMissionIdList(Collection<Long> missionIdList);
}
