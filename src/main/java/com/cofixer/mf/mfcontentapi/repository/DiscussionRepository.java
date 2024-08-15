package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.Discussion;
import com.cofixer.mf.mfcontentapi.repository.query.DiscussionQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long>, DiscussionQueryRepository {
    Discussion findByStateId(Long stateId);
}
