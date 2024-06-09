package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.FamilyMissionDetail;

import java.util.Collection;
import java.util.List;

public interface FamilyMissionDetailQueryRepository {

    FamilyMissionDetail getFamilyMission(Long missionId, Long familyId);

    List<FamilyMissionDetail> getMissionsInPeriod(Collection<Long> scheduleIds);
}
