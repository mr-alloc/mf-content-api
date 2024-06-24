package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "미션 댓글 생성 요청")
public record CreateCommentReq(

        @Min(1)
        @Schema(description = "미션 ID")
        Long missionId,

        @Schema(description = "댓글 내용")
        String content,

        @Schema(description = "상태 생성 일시")
        Long timestamp
) {
}
