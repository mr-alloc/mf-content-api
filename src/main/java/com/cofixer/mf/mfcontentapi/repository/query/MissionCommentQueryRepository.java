package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionComment;

import java.util.List;

public interface MissionCommentQueryRepository {
    List<MissionComment> getCommentsByStateId(Long stateId);
}
