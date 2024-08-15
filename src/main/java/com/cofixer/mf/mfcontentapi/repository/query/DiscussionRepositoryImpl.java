package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Discussion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QDiscussion.discussion;

@RequiredArgsConstructor
@Repository
public class DiscussionRepositoryImpl implements DiscussionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Discussion> getDiscussionsByStateIds(Collection<Long> states) {
        return queryFactory.selectFrom(discussion)
                .where(discussion.stateId.in(states))
                .fetch();
    }
}
