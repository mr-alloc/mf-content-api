package com.cofixer.mf.mfcontentapi.dto.res;

public record ChangeFamilyMissionRes(

        FamilyMissionDetailValue changed
) {
    public static ChangeFamilyMissionRes of(FamilyMissionDetailValue changed) {
        return new ChangeFamilyMissionRes(changed);
    }
}
