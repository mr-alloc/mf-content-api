package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateCategoryReq;
import com.cofixer.mf.mfcontentapi.dto.res.ScheduleCategoryValue;
import com.cofixer.mf.mfcontentapi.manager.ScheduleCategoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleCategoryService {

    private final ScheduleCategoryManager manager;


    public List<ScheduleCategoryValue> getCategories(AuthorizedMember authorizedMember) {
        List<ScheduleCategory> categories = authorizedMember.forFamilyMember()
                ? manager.getFamilyCategories(authorizedMember.getFamilyId())
                : manager.getUserCategories(authorizedMember.getMemberId());

        return categories.stream()
                .map(ScheduleCategoryValue::of)
                .toList();
    }

    @Transactional
    public ScheduleCategory addCategory(
            AuthorizedMember authorizedMember,
            CreateCategoryReq request
    ) {
        ScheduleCategory category = ScheduleCategory.of(authorizedMember, request.name(), request.color(), request.description());
        return manager.addCategory(category);
    }
}
