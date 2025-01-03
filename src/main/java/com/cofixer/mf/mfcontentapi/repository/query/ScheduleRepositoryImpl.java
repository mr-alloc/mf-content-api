package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.domain.Schedule;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QMission.mission;
import static com.cofixer.mf.mfcontentapi.domain.QSchedule.schedule;

@Slf4j
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
            ScheduleType scheduleType,
            Long timestamp
    ) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(schedule.startAt.loe(timestamp)
                .and(schedule.endAt.goe(timestamp)));
        if (authorizedMember.forFamilyMember()) {
            condition.and(schedule.family.eq(authorizedMember.getFamilyId()));
        } else {
            condition.and(schedule.reporter.eq(authorizedMember.getMemberId()));
        }

        return queryFactory.selectFrom(schedule)
                .where(condition.and(schedule.type.eq(scheduleType.getValue())))
                .orderBy(schedule.startAt.desc())
                .fetch();
    }

    @Override
    public Schedule getScheduleByMissionId(Long missionId) {
        return queryFactory
                .selectFrom(schedule)
                .join(mission).on(mission.scheduleId.eq(schedule.id))
                .where(mission.id.eq(missionId))
                .fetchOne();
    }

    @Override
    public List<Schedule> getAllSchedules(AuthorizedMember authorizedMember, ScheduleType scheduleType) {
        BooleanBuilder condition = new BooleanBuilder();
        if (authorizedMember.forFamilyMember()) {
            condition.and(schedule.family.eq(authorizedMember.getFamilyId()));
        } else {
            condition.and(schedule.reporter.eq(authorizedMember.getMemberId()));
        }
        return queryFactory
                .selectFrom(schedule)
                .where(condition.and(schedule.type.eq(scheduleType.getValue())))
                .fetch();
    }
}
