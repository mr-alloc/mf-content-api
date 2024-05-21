package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;
import com.cofixer.mf.mfcontentapi.repository.query.FamilyMissionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpandedFamilyMissionRepository extends JpaRepository<ExpandedFamilyMission, Long>, FamilyMissionQueryRepository {

    Optional<ExpandedFamilyMission> findByIdAndFamilyId(Long id, Long familyId);
}
