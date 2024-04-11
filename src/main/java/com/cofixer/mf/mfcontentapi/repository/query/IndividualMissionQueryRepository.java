package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;

import java.util.List;

public interface IndividualMissionQueryRepository {

    List<IndividualMission> findPeriodMissions(Long mid, long startTime, long endTime);
}
