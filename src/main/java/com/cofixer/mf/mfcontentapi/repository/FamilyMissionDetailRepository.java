package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.FamilyMissionDetail;
import com.cofixer.mf.mfcontentapi.repository.query.FamilyMissionDetailQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMissionDetailRepository
        extends JpaRepository<FamilyMissionDetail, Long>, FamilyMissionDetailQueryRepository {

}
