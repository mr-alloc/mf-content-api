package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.SelectorType;
import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QScheduleCategory.scheduleCategory;

@RequiredArgsConstructor
@Repository
public class ScheduleCategoryRepositoryImpl implements ScheduleCategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScheduleCategory> getOwnCategories(SelectorType selectorType, Long selector) {
        return queryFactory
                .selectFrom(scheduleCategory)
                .where(scheduleCategory.selectorType.eq(selectorType.getValue())
                        .and(scheduleCategory.selector.eq(selector)))
                .fetch();
    }
}
