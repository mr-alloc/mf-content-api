package com.cofixer.mf.mfcontentapi.exception;

import lombok.Getter;

import java.io.Serial;

public abstract class CommonException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 540702681430167766L;

    public CommonException(String message) {
        super(message);
    }

    public abstract int getCode();
}
