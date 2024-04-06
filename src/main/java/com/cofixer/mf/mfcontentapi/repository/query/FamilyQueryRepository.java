package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.dto.res.FamilySummary;

import java.util.List;

public interface FamilyQueryRepository {

    List<FamilySummary> findOwnFamilies(Long mid);
}
