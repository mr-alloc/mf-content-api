package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DeclaredValidateResult implements DeclaredResult {
    FAILED_AT_COMMON_VALIDATION(-99, HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private HttpStatus httpStatus;

    DeclaredValidateResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
