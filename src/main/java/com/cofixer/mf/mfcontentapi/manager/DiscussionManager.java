package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.Discussion;
import com.cofixer.mf.mfcontentapi.repository.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiscussionManager {

    private final DiscussionRepository repository;

    public Discussion getDiscussion(Long discussionId) {
        return repository.findById(discussionId)
                .orElse(null);
    }

    public Discussion getDiscussionByStateId(Long stateId) {
        return repository.findByStateId(stateId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Discussion saveDiscussion(Discussion discussion) {
        return repository.save(discussion);
    }

    public List<Discussion> getDiscussionAll(Collection<Long> states) {
        return repository.getDiscussionsByStateIds(states);
    }
}
