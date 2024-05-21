package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;

import java.util.List;

public interface FamilyMissionQueryRepository {
    List<ExpandedFamilyMission> findPeriodMissions(AuthorizedMember familyId, long startTime, long endTime);
}
