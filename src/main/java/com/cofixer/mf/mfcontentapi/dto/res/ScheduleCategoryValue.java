package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ScheduleCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스케줄 카테고리 정보")
public record ScheduleCategoryValue(

        @Schema(description = "카테고리 ID")
        Long categoryId,

        @Schema(description = "이름")
        String name,

        @Schema(description = "색상")
        String color,

        @Schema(description = "설명")
        String description,

        @Schema(description = "배치 순서")
        Integer order
) {

    public static ScheduleCategoryValue of(ScheduleCategory category) {
        return new ScheduleCategoryValue(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getDescription(),
                category.getSortOrder()
        );
    }
}
