package com.cofixer.mf.mfcontentapi.dto.res;

public record DeleteFamilyMissionRes(
        Long missionId
) {
    public static DeleteFamilyMissionRes of(Long missionId) {
        return new DeleteFamilyMissionRes(missionId);
    }
}
