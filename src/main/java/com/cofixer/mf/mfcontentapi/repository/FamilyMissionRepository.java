package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;
import com.cofixer.mf.mfcontentapi.repository.query.FamilyMissionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMissionRepository extends JpaRepository<FamilyMission, Long>, FamilyMissionQueryRepository {
}
