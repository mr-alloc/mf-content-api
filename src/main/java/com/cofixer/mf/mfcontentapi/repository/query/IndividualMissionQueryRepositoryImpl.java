package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QIndividualMission.individualMission;

@Repository
@RequiredArgsConstructor
public class IndividualMissionQueryRepositoryImpl implements IndividualMissionQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<IndividualMission> findPeriodMissions(Long mid, long startTime, long endTime) {
        return queryFactory.selectFrom(individualMission)
                .where(individualMission.assigneeId.eq(mid)
                        .and(individualMission.deadLine.between(startTime, endTime)))
                .fetch();
    }
}