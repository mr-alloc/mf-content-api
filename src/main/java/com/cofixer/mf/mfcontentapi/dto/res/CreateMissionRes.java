package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;

public record CreateMissionRes(
        /* 생성된 미션 ID */
        Long createdMissionId
) {
    public static CreateMissionRes of(ExpandedMission mission) {
        return new CreateMissionRes(mission.getId());
    }
}
