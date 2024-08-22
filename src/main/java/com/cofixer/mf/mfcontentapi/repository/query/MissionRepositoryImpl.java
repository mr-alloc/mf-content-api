package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.repository.MissionQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.cofixer.mf.mfcontentapi.domain.QMission.mission;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MissionRepositoryImpl implements MissionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Mission> getMissionsByIds(Set<Long> missionIds) {
        return queryFactory.selectFrom(mission)
                .where(mission.id.in(missionIds))
                .fetch();
    }
}
