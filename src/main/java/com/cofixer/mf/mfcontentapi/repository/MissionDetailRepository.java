package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.MissionDetail;
import com.cofixer.mf.mfcontentapi.repository.query.MissionDetailQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionDetailRepository extends JpaRepository<MissionDetail, Long>, MissionDetailQueryRepository {

}
