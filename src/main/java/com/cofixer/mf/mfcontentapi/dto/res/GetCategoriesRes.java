package com.cofixer.mf.mfcontentapi.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "카테고리 목록 정보")
public record GetCategoriesRes (

        @Schema(description = "카테고리 목록")
        List<ScheduleCategoryValue> categories
) {

    public static GetCategoriesRes of(List<ScheduleCategoryValue> categories) {
        return new GetCategoriesRes(categories);
    }
}
