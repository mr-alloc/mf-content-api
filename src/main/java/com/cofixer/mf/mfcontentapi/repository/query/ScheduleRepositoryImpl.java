package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> getSchedulesByRange(AuthorizedMember authorizedMember, Long startAt, Long endAt) {
        BooleanBuilder condition = new BooleanBuilder(schedule.reporter.eq(authorizedMember.getMemberId()));
        if (authorizedMember.forFamilyMember()) {
            condition.and(schedule.family.eq(authorizedMember.getFamilyId()));
        }
        condition.and(
                // startAt <= schedule.startAt <= endAt
                schedule.startAt.goe(startAt).and(schedule.endAt.loe(startAt))
                        // startAt <= schedule.endAt <= endAt
                        .or(schedule.startAt.goe(startAt).and(schedule.endAt.loe(endAt)))
        );

        return queryFactory.select(schedule)
                .from(schedule)
                .where(condition)
                .fetch();
    }
}
