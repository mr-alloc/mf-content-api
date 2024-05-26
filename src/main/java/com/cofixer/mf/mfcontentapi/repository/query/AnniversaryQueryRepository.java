package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.cofixer.mf.mfcontentapi.dto.AnniversarySearchFilter;

import java.util.List;

public interface AnniversaryQueryRepository {
    List<Anniversary> getAnniversariesBySearchFilter(AnniversarySearchFilter searchFilter);
}
