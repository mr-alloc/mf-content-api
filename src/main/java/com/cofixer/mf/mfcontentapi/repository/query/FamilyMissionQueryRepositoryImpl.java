package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QFamilyMission.familyMission;

@Repository
@RequiredArgsConstructor
public class FamilyMissionQueryRepositoryImpl implements FamilyMissionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FamilyMission> findPeriodMissions(AuthorizedMember member, long startTime, long endTime) {
        return queryFactory.selectFrom(familyMission)
                .where(familyMission.familyId.eq(member.getFamilyId()))
                .fetch();
    }
}
