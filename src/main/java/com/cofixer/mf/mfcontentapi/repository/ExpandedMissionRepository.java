package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;
import com.cofixer.mf.mfcontentapi.repository.query.ExpandedMissionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpandedMissionRepository extends JpaRepository<ExpandedMission, Long>, ExpandedMissionQueryRepository {

}
