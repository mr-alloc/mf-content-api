package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Anniversary;

import java.util.Collection;
import java.util.List;

public interface AnniversaryQueryRepository {
    List<Anniversary> getAnniversariesBySearchFilter(Collection<Long> schedules);
}
