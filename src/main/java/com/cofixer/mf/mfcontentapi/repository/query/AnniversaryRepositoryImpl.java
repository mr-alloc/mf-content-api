package com.cofixer.mf.mfcontentapi.repository.query;


import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.cofixer.mf.mfcontentapi.dto.AnniversarySearchFilter;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QAnniversary.anniversary;

@Repository
@RequiredArgsConstructor
public class AnniversaryRepositoryImpl implements AnniversaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Anniversary> getAnniversariesBySearchFilter(AnniversarySearchFilter searchFilter) {
        BooleanBuilder condition = new BooleanBuilder(anniversary.reporter.eq(searchFilter.reporterId()));

        if (searchFilter.isFamily()) {
            condition.and(anniversary.family.eq(searchFilter.familyId()));
        }

        LocalDate firstDay = LocalDate.of(searchFilter.year(), searchFilter.month(), 1);
        LocalDate lastDay = firstDay.plusMonths(1).minusDays(1);


        long start = TemporalUtil.toTimeStamp(LocalDateTime.of(firstDay, LocalTime.MIN));
        long end = TemporalUtil.toTimeStamp(LocalDateTime.of(lastDay, LocalTime.MAX));
        condition.and(
                anniversary.yearMonth.eq(searchFilter.yearMonth())
                        .or(
                                anniversary.startAt.between(start, end).and(anniversary.endAt.between(start, end))
                        )
        );

        return queryFactory.selectFrom(anniversary)
                .where(condition)
                .fetch();
    }
}
