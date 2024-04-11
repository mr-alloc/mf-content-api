package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;

import java.util.List;

public interface FamilyMissionQueryRepository {
    List<FamilyMission> findPeriodMissions(AuthorizedMember familyId, long startTime, long endTime);
}
