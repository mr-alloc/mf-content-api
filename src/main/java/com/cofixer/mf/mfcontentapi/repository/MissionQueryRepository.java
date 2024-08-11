package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.Mission;

import java.util.List;
import java.util.Set;

public interface MissionQueryRepository {

    List<Mission> getMissionsByIds(Set<Long> missionIds);
}
