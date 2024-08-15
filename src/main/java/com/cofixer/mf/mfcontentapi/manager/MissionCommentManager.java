package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.MissionComment;
import com.cofixer.mf.mfcontentapi.repository.MissionCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MissionCommentManager {

    private final MissionCommentRepository repository;

    public MissionComment saveComment(MissionComment toBeSaved) {
        return repository.save(toBeSaved);
    }

    public List<MissionComment> getComments(Long stateId) {
        return repository.getCommentsByStateId(stateId);
    }
}
