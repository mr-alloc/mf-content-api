package com.cofixer.mf.mfcontentapi.dto.res;

public record ChangeMissionRes(

        MissionDetailValue changed
) {
    public static ChangeMissionRes of(MissionDetailValue changed) {
        return new ChangeMissionRes(changed);
    }
}
