package com.cofixer.mf.mfcontentapi.repository.query;


import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QAnniversary.anniversary;

@Repository
@RequiredArgsConstructor
public class AnniversaryRepositoryImpl implements AnniversaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Anniversary> getAnniversariesBySearchFilter(Collection<Long> schedules) {
        return queryFactory.selectFrom(anniversary)
                .where(anniversary.scheduleId.in(schedules))
                .fetch();
    }
}
