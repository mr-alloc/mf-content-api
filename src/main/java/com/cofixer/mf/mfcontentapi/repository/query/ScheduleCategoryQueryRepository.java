package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.SelectorType;
import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;

import java.util.List;

public interface ScheduleCategoryQueryRepository {

    List<ScheduleCategory> getOwnCategories(SelectorType selectorType, Long selector);
}
