package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Family;

import java.util.List;

public interface FamilyQueryRepository {

    List<Family> findOwnFamilies(Long mid);
}
