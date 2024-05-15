package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;

import java.io.Serial;

public class MissionException extends CommonException {

    @Serial
    private static final long serialVersionUID = -6479891692858306445L;
    private final DeclaredMissionResult missionResult;

    public MissionException(DeclaredMissionResult missionResult) {
        super(missionResult.name());
        this.missionResult = missionResult;
    }

    public MissionException(DeclaredMissionResult missionResult, String message) {
        super(message);
        this.missionResult = missionResult;
    }

    @Override
    public int getCode() {
        return missionResult.getCode();
    }
}
