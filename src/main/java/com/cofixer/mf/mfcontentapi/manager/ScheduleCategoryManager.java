package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.SelectorType;
import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;
import com.cofixer.mf.mfcontentapi.repository.ScheduleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleCategoryManager {

    private final ScheduleCategoryRepository repository;

    public List<ScheduleCategory> getFamilyCategories(Long familyId) {
        return repository.getOwnCategories(SelectorType.FAMILY, familyId);
    }

    public List<ScheduleCategory> getUserCategories(Long userId) {
        return repository.getOwnCategories(SelectorType.USER, userId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ScheduleCategory addCategory(ScheduleCategory category) {
        return repository.save(category);
    }
}
