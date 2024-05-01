package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest.Id;
import static com.cofixer.mf.mfcontentapi.domain.QFamilyMemberConnectRequest.familyMemberConnectRequest;

@Repository
@RequiredArgsConstructor
public class FamilyMemberConnectRequestQueryRepositoryImpl implements FamilyMemberConnectRequestQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean hasRequested(FamilyMemberConnectRequest.Id id) {
        return queryFactory.selectFrom(familyMemberConnectRequest)
                .where(familyMemberConnectRequest.id.eq(id))
                .fetchOne() != null;
    }

    @Override
    public List<FamilyMemberConnectRequest> getConnectRequests(FamilyMemberId familyMemberId, FamilyMemberDirection direction) {
        BooleanBuilder findCondition = new BooleanBuilder();

        if (familyMemberId.hasFamilyId()) {
            findCondition.and(familyMemberConnectRequest.id.familyId.eq(familyMemberId.getFamilyId()));
        }

        if (familyMemberId.hasMemberId()) {
            findCondition.and(familyMemberConnectRequest.id.memberId.eq(familyMemberId.getMemberId()));
        }

        return queryFactory.selectFrom(familyMemberConnectRequest)
                .where(findCondition.and(familyMemberConnectRequest.id.connectDirection.eq(direction.getValue())))
                .fetch();
    }

    @Override
    public FamilyMemberConnectRequest getConnectRequest(Id id) {
        return queryFactory.selectFrom(familyMemberConnectRequest)
                .where(familyMemberConnectRequest.id.eq(id))
                .fetchOne();
    }

    @Override
    public void deleteAllRequests(Long familyId, Long memberId) {
        queryFactory.delete(familyMemberConnectRequest)
                .where(familyMemberConnectRequest.id.familyId.eq(familyId)
                        .and(familyMemberConnectRequest.id.memberId.eq(memberId)))
                .execute();
    }
}
