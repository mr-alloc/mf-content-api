package com.cofixer.mf.mfcontentapi.dto.res;

public record DeleteMissionRes(
        Long missionId
) {
    public static DeleteMissionRes of(Long missionId) {
        return new DeleteMissionRes(missionId);
    }
}
