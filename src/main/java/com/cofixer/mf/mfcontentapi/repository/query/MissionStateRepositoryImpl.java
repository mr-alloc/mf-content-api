package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.MissionState;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QMission.mission;
import static com.cofixer.mf.mfcontentapi.domain.QMissionState.missionState;

@RequiredArgsConstructor
@Repository
public class MissionStateRepositoryImpl implements MissionStateQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MissionState> getStateAllByMissionId(Long missionId) {
        return queryFactory.selectFrom(missionState)
                .where(missionState.missionId.eq(missionId))
                .fetch();
    }

    @Override
    public MissionState getState(Long missionId, Long startStamp) {
        return queryFactory.selectFrom(missionState)
                .where(missionState.missionId.eq(missionId), missionState.startStamp.eq(startStamp))
                .fetchOne();
    }

    @Override
    public List<MissionState> getStateAllByMissionIdList(Collection<Long> missionIdList) {
        return queryFactory.selectFrom(missionState)
                .where(missionState.missionId.in(missionIdList))
                .fetch();
    }

    @Override
    public List<MissionState> getStateAllByScheduleIds(Collection<Long> scheduleIds) {
        return queryFactory
                .select(missionState)
                .from(mission)
                .join(missionState).on(mission.missionId.eq(missionState.missionId))
                .where(mission.scheduleId.in(scheduleIds))
                .fetch();
    }
}
