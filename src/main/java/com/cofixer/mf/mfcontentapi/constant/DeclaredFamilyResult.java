package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeclaredFamilyResult implements DeclaredResult {

    BLANK_FAMILY_NAME(-1, HttpStatus.BAD_REQUEST),
    FAMILY_NAME_RULE_VIOLATION(-2, HttpStatus.BAD_REQUEST),
    CANNOT_CHANGE_SAME_NICKNAME(-3, HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private HttpStatus httpStatus;

    DeclaredFamilyResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
