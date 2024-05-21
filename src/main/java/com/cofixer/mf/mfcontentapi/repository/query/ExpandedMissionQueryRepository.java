package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;

import java.util.List;

public interface ExpandedMissionQueryRepository {

    List<ExpandedMission> findPeriodMissions(Long mid, long startTime, long endTime);
}
