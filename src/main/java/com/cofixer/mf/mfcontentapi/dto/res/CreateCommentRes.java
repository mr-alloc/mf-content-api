package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.dto.MissionCommentValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 생성 응답")
public record CreateCommentRes(
        @Schema(description = "생성된 댓글 정보")
        MissionCommentValue created
) {
    public static CreateCommentRes of(MissionCommentValue created) {
        return new CreateCommentRes(created);
    }
}
