package com.cofixer.mf.mfcontentapi.dto.res;

public record GetMissionDetailRes(
        MissionDetailValue mission
) {
    public static GetMissionDetailRes of(MissionDetailValue mission) {
        return new GetMissionDetailRes(mission);
    }
}
