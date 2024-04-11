package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
import com.cofixer.mf.mfcontentapi.repository.query.IndividualMissionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualMissionRepository extends JpaRepository<IndividualMission, Long>, IndividualMissionQueryRepository {

}
