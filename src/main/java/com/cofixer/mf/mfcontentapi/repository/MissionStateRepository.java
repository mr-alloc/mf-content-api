package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.MissionState;
import com.cofixer.mf.mfcontentapi.repository.query.MissionStateQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionStateRepository extends JpaRepository<MissionState, Long>, MissionStateQueryRepository {

}
