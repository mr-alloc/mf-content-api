package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionDetail;

import java.util.Collection;
import java.util.List;

public interface MissionDetailQueryRepository {

    List<MissionDetail> getMissionsInPeriod(Collection<Long> scheduleIds);
}
