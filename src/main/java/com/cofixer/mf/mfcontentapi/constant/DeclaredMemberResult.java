package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeclaredMemberResult implements DeclaredResult {

    /**
     * -1: 회원을 찾을 수 없음
     */
    NOT_FOUND_MEMBER(-1),
    NOT_ALLOWED_ROLE(-2, HttpStatus.FORBIDDEN),
    NICKNAME_RULE_VIOLATION(-3, HttpStatus.BAD_REQUEST),
    ALREADY_INITIALIZED(-4, HttpStatus.BAD_REQUEST),
    NOT_FOUND_CONNECT_REQUEST(-5),
    NOT_FOUND_FAMILY(-6),
    ALREADY_AFFILIATED_FAMILY(-7);

    private final int code;
    private HttpStatus httpStatus;

    DeclaredMemberResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
