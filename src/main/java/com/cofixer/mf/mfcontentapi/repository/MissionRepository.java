package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long>, MissionQueryRepository {

    List<Mission> findByMissionIdIn(Collection<Long> ids);

}
