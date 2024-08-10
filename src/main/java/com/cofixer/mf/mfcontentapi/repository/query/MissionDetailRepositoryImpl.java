package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QMission.mission;
import static com.cofixer.mf.mfcontentapi.domain.QMissionDetail.missionDetail;
import static com.cofixer.mf.mfcontentapi.domain.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class MissionDetailRepositoryImpl implements MissionDetailQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<MissionDetail> getMissionsInPeriod(Collection<Long> scheduleIds) {
        return queryFactory.selectFrom(missionDetail)
                .join(mission).on(missionDetail.missionId.eq(mission.missionId))
                .join(schedule).on(mission.scheduleId.eq(schedule.scheduleId))
                .where(schedule.scheduleId.in(scheduleIds))
                .fetch();
    }
}
