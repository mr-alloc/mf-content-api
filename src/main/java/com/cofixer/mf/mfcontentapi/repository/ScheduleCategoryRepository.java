package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;
import com.cofixer.mf.mfcontentapi.repository.query.ScheduleCategoryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCategoryRepository extends JpaRepository<ScheduleCategory, Long>, ScheduleCategoryQueryRepository {
}
