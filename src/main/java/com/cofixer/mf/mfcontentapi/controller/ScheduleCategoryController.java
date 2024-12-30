package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateCategoryReq;
import com.cofixer.mf.mfcontentapi.dto.res.GetCategoriesRes;
import com.cofixer.mf.mfcontentapi.dto.res.ScheduleCategoryValue;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.ScheduleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "/v1/sc", description = "스케줄 카테고리")
@AccountAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/schedule-category")
public class ScheduleCategoryController {

    private final ScheduleCategoryService service;

    @GetMapping
    @Operation(summary = "목록 조회")
    public ResponseEntity<GetCategoriesRes> getCategories() {
        AuthorizedMember member = AuthorizedService.getMember();
        List<ScheduleCategoryValue> categories = service.getCategories(member);

        return ResponseEntity.ok(GetCategoriesRes.of(categories));
    }

    @PostMapping
    @Operation(summary = "추가")
    public ResponseEntity<Void> addCategories(
            @RequestBody
            CreateCategoryReq request
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        service.addCategory(authorizedMember, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
