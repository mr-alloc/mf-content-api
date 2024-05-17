package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import lombok.Getter;

import java.io.Serial;

@Getter
public class MissionException extends CommonException {

    @Serial
    private static final long serialVersionUID = -6479891692858306445L;
    private final DeclaredMissionResult result;

    public MissionException(DeclaredMissionResult result) {
        super(result.name());
        this.result = result;
    }

    public MissionException(DeclaredMissionResult result, String message) {
        super(message);
        this.result = result;
    }

    @Override
    public int getCode() {
        return result.getCode();
    }
}
