package com.cofixer.mf.mfcontentapi.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "전체 토론조회 정보")
public record GetDiscussionsRes(
        @Schema(description = "참여중인 토론 목록")
        List<DiscussionValue> discussions
) {

    public static GetDiscussionsRes of(List<DiscussionValue> discussions) {
        return new GetDiscussionsRes(discussions);
    }
}
