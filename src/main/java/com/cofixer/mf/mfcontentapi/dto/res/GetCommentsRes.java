package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.dto.MissionCommentValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "댓글 조회응답")
public record GetCommentsRes(

        @Schema(description = "댓글목록")
        List<MissionCommentValue> comments
) {
    public static GetCommentsRes of(List<MissionCommentValue> comments) {
        return new GetCommentsRes(comments);
    }
}
