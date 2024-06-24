package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.domain.MissionComment;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미션 댓글 정보")
public record MissionCommentValue(
        @Schema(description = "댓글 ID")
        Long id,

        @Schema(description = "작성자 ID")
        Long memberId,

        @Schema(description = "댓글 내용")
        String content,

        @Schema(description = "작성일시")
        Long createdAt
) {
    public static MissionCommentValue of(MissionComment missionComment) {
        return new MissionCommentValue(
                missionComment.getId(),
                missionComment.getMemberId(),
                missionComment.getContent(),
                missionComment.getCreatedAt()
        );
    }
}
