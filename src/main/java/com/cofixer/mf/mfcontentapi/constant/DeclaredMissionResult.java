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
    NO_CHANGED_TARGET(-4, HttpStatus.BAD_REQUEST),
    NOT_OWN_MISSION(-5, HttpStatus.FORBIDDEN),
    CANNOT_CHANGE_TO_DELETE(-6, HttpStatus.BAD_REQUEST),
    NOT_FOUND_STATE(-7, HttpStatus.BAD_REQUEST),
    NOT_FOUND_MISSION(-8, HttpStatus.BAD_REQUEST),
    NOT_SCHEDULE_RANGE(-9, HttpStatus.BAD_REQUEST);
    private final int code;
    private HttpStatus httpStatus;

    DeclaredMissionResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
