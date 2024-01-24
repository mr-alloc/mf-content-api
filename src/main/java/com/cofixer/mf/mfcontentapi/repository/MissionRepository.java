package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
}
