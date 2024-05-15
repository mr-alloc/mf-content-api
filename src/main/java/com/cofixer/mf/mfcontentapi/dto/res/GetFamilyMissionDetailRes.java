package com.cofixer.mf.mfcontentapi.dto.res;

public record GetFamilyMissionDetailRes(
        FamilyMissionDetailValue mission
) {

    public static GetFamilyMissionDetailRes of(FamilyMissionDetailValue missionDetail) {
        return new GetFamilyMissionDetailRes(missionDetail);
    }
}
