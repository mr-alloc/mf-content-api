package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
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
    public List<Schedule> getSchedulesByRange(AuthorizedMember authorizedMember, Long startAt, Long endAt, ScheduleType scheduleType) {
        BooleanBuilder condition = new BooleanBuilder(schedule.reporter.eq(authorizedMember.getMemberId()));
        if (authorizedMember.forFamilyMember()) {
            condition.and(schedule.family.eq(authorizedMember.getFamilyId()));
        }
        condition.and(
                        schedule.mode.in(ScheduleMode.PERIOD.getValue(), ScheduleMode.REPEAT.getValue())
                                //범위 조건이면
                                .and(

                                        schedule.startAt.loe(startAt).and(schedule.endAt.goe(endAt))
                                                .or(schedule.startAt.loe(startAt).and(schedule.endAt.loe(endAt)))
                                                .or(schedule.startAt.goe(startAt).and(schedule.endAt.goe(endAt)))
                                                .or(schedule.startAt.goe(startAt).and(schedule.endAt.loe(endAt)))
                                )
                                .or(
                                        // startAt <= schedule.startAt <= endAt
                                        schedule.startAt.goe(startAt).and(schedule.endAt.loe(endAt))
                                )
                )
                .and(schedule.type.eq(scheduleType.getValue()));

        return queryFactory.select(schedule)
                .from(schedule)
                .where(condition)
                .fetch();
    }
}
