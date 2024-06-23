package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.MissionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionCommentRepository extends JpaRepository<MissionComment, Long> {

    List<MissionComment> findAllByStateId(Long stateId);
}
