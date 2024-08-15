package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QDiscussion.discussion;
import static com.cofixer.mf.mfcontentapi.domain.QMissionComment.missionComment;

@RequiredArgsConstructor
@Repository
public class MissionCommentRepositoryImpl implements MissionCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MissionComment> getCommentsByStateId(Long stateId) {
        return queryFactory
                .selectFrom(missionComment)
                .join(discussion).on(missionComment.discussionId.eq(discussion.id))
                .where(discussion.stateId.eq(stateId))
                .fetch();
    }
}
