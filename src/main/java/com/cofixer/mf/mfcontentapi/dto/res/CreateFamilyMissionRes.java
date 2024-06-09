package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record CreateFamilyMissionRes(
        List<FamilyMissionDetailValue> created
) {
    public static CreateFamilyMissionRes of(List<FamilyMissionDetailValue> created) {
        return new CreateFamilyMissionRes(created);
    }
}
