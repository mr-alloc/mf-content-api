package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.FamilyMissionDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QFamilyMissionDetail.familyMissionDetail;
import static com.cofixer.mf.mfcontentapi.domain.QMission.mission;
import static com.cofixer.mf.mfcontentapi.domain.QSchedule.schedule;


@Repository
@RequiredArgsConstructor
public class FamilyMissionDetailRepositoryImpl implements FamilyMissionDetailQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public FamilyMissionDetail getFamilyMission(Long missionId, Long familyId) {
        return queryFactory.selectFrom(familyMissionDetail)
                .join(mission).on(familyMissionDetail.missionId.eq(mission.missionId))
                .join(schedule).on(mission.scheduleId.eq(schedule.scheduleId))
                .where(familyMissionDetail.missionId.eq(missionId)
                        .and(schedule.family.eq(familyId))
                )
                .fetchOne();
    }

    @Override
    public List<FamilyMissionDetail> getMissionsInPeriod(Collection<Long> scheduleIds) {
        return queryFactory.selectFrom(familyMissionDetail)
                .join(mission).on(familyMissionDetail.missionId.eq(mission.missionId))
                .join(schedule).on(mission.scheduleId.eq(schedule.scheduleId))
                .where(schedule.scheduleId.in(scheduleIds))
                .fetch();
    }
}
