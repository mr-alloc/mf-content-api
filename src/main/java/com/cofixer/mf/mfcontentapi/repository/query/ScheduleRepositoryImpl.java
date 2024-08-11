package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
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
        BooleanBuilder condition = new BooleanBuilder();
        if (authorizedMember.forFamilyMember()) {
            condition.and(schedule.family.eq(authorizedMember.getFamilyId()));
        } else {
            condition.and(schedule.reporter.eq(authorizedMember.getMemberId()));
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

        return queryFactory.selectFrom(schedule)
                .where(condition)
                .fetch();
    }

    @Override
    public List<Schedule> getComingSchedules(
            AuthorizedMember authorizedMember,
            ScheduleType scheduleType
    ) {
        BooleanBuilder condition = new BooleanBuilder();

        long now = TemporalUtil.getEpochSecond();
        long tenDay = TemporalUtil.DAY_IN_SECONDS * 10;
        condition.and(schedule.startAt.goe(now).and(schedule.startAt.loe(now + tenDay)));
        if (authorizedMember.forFamilyMember()) {
            condition.and(schedule.family.eq(authorizedMember.getFamilyId()));
        } else {
            condition.and(schedule.reporter.eq(authorizedMember.getMemberId()));
        }

        return queryFactory.selectFrom(schedule)
                .where(condition.and(schedule.type.eq(scheduleType.getValue())))
                .limit(30)
                .orderBy(schedule.startAt.desc())
                .fetch();
    }
}
