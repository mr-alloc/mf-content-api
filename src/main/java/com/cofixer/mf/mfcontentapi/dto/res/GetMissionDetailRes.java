package com.cofixer.mf.mfcontentapi.dto.res;

public record GetMissionDetailRes(
        MissionValue mission
) {
    public static GetMissionDetailRes of(MissionValue mission) {
        return new GetMissionDetailRes(mission);
    }
}
