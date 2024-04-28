package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;

public record CreateFamilyMissionRes(
        Long createdId
) {
    public static CreateFamilyMissionRes of(FamilyMission newerMission) {
        return new CreateFamilyMissionRes(newerMission.getFamilyId());
    }
}
