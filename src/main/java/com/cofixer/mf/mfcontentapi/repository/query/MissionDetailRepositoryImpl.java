package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QMission.mission;
import static com.cofixer.mf.mfcontentapi.domain.QMissionDetail.missionDetail;

@Repository
@RequiredArgsConstructor
public class MissionDetailRepositoryImpl implements MissionDetailQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<MissionDetail> getMissionsInPeriod(Collection<Long> scheduleIds) {
        return queryFactory.select(missionDetail)
                .from(mission)
                .join(missionDetail).on(mission.missionId.eq(missionDetail.missionId))
                .where(mission.scheduleId.in(scheduleIds))
                .fetch();
    }
}
