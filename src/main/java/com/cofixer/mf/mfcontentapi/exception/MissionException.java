package com.cofixer.mf.mfcontentapi.exception;

import java.io.Serial;

public class MissionException extends CommonException {

    @Serial
    private static final long serialVersionUID = -6479891692858306445L;

    public MissionException(String message) {
        super(message);
    }

    @Override
    public int getCode() {
        return 0;
    }
}
