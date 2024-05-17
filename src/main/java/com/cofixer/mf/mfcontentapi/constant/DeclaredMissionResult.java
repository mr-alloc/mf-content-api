package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeclaredMissionResult implements DeclaredResult {

    NOT_FOUND_INDIVIDUAL_MISSION(-1, HttpStatus.BAD_REQUEST),
    NOT_FOUND_FAMILY_MISSION(-2, HttpStatus.BAD_REQUEST),
    NOT_FOUND_MEMBER(-3, HttpStatus.BAD_REQUEST),
    NO_CHANGED_TARGET(-4, HttpStatus.BAD_REQUEST);
    private final int code;
    private HttpStatus httpStatus;

    DeclaredMissionResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}