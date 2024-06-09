package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record CreateMissionRes(
        List<MissionDetailValue> created
) {
    public static CreateMissionRes of(List<MissionDetailValue> created) {
        return new CreateMissionRes(created);
    }
}
