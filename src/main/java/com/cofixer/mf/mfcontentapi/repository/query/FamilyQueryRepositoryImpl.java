package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Family;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FamilyQueryRepositoryImpl implements FamilyQueryRepository {

    @Override
    public List<Family> findOwnFamilies(Long mid) {

        return null;
    }
}
