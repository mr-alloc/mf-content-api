package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QExpandedFamilyMission.expandedFamilyMission;

@Repository
@RequiredArgsConstructor
public class FamilyMissionQueryRepositoryImpl implements FamilyMissionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ExpandedFamilyMission> findPeriodMissions(AuthorizedMember member, long startTime, long endTime) {
        return queryFactory.selectFrom(expandedFamilyMission)
                .where(expandedFamilyMission.familyId.eq(member.getFamilyId())
                        .and(expandedFamilyMission.mission.startDueStamp.between(startTime, endTime))
                        .and(expandedFamilyMission.mission.status.ne(MissionStatus.DELETED.getCode()))
                )
                .fetch();
    }
}
