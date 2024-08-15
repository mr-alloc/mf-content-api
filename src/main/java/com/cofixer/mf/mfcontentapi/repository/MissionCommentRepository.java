package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.MissionComment;
import com.cofixer.mf.mfcontentapi.repository.query.MissionCommentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionCommentRepository extends JpaRepository<MissionComment, Long>, MissionCommentQueryRepository {

}
