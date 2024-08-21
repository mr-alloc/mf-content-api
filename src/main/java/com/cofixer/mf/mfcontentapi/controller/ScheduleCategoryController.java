package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.res.GetCategoriesRes;
import com.cofixer.mf.mfcontentapi.dto.res.ScheduleCategoryValue;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.ScheduleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "/v1/sc: 스케줄 카테고리")
@AccountAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/schedule-category")
public class ScheduleCategoryController {

    private final ScheduleCategoryService service;

    @GetMapping
    @Operation(summary = "/: 카테고리 목록 조회")
    public ResponseEntity<GetCategoriesRes> getCategories() {
        AuthorizedMember member = AuthorizedService.getMember();
        List<ScheduleCategoryValue> categories = service.getCategories(member);

        return ResponseEntity.ok(GetCategoriesRes.of(categories));
    }

}
