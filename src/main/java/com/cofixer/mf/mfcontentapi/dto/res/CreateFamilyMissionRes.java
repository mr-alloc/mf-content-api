package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;

public record CreateFamilyMissionRes(
        Long createdId
) {
    public static CreateFamilyMissionRes of(ExpandedFamilyMission newerMission) {
        return new CreateFamilyMissionRes(newerMission.getFamilyId());
    }
}
