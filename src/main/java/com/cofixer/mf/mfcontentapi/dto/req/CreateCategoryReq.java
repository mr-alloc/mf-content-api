package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(description = "카테고리 생성요청")
public record CreateCategoryReq(
        @NotEmpty
        @Schema(description = "카테고리명")
        String name,

        @Pattern(regexp = "^(?:[0-9a-fA-F]{3}){1,2}$")
        @Schema(description = "컬러")
        String color,

        @Schema(description = "설명")
        String description
) {
}
