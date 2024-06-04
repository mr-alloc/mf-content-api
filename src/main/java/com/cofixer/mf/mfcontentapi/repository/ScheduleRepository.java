package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.repository.query.ScheduleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleQueryRepository {
}
