package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Discussion;
import com.cofixer.mf.mfcontentapi.domain.MissionState;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토론 정보")
public record DiscussionValue(
        @Schema(description = "토론 아이디")
        Long id,

        Long missionId,

        @Schema(description = "토론방 제목")
        String name,

        @Schema(description = "마지막 메세지")
        String latestMessage,

        @Schema(description = "마지막 보낸 시간")
        Long latestUpdateTime,

        @Schema(description = "미션 시작일")
        Long stateScheduleTime
) {

    public static DiscussionValue of(Discussion discussion, MissionState missionState) {
        return new DiscussionValue(
                discussion.getId(),
                missionState.getMissionId(),
                discussion.getName(),
                discussion.getLatestMessage(),
                discussion.getLatestUpdatedAt(),
                missionState.getStartStamp()
        );
    }
}
