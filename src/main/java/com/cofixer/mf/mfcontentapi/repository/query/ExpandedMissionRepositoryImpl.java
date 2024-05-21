package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QExpandedMission.expandedMission;

@Repository
@RequiredArgsConstructor
public class ExpandedMissionRepositoryImpl implements ExpandedMissionQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<ExpandedMission> findPeriodMissions(Long mid, long startTime, long endTime) {
        return queryFactory.selectFrom(expandedMission)
                .where(expandedMission.mission.reporterId.eq(mid)
                        .and(expandedMission.mission.startDueStamp.between(startTime, endTime))
                        .and(expandedMission.mission.status.ne(MissionStatus.DELETED.getCode()))
                )
                .fetch();
    }
}
