package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Discussion;

import java.util.Collection;
import java.util.List;

public interface DiscussionQueryRepository {
    List<Discussion> getDiscussionsByStateIds(Collection<Long> states);
}
