package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeclaredMemberResult {

    /**
     * -1: 회원을 찾을 수 없음
     */
    NOT_FOUND_MEMBER(-1),
    NOT_AUTHORIZED_ROLE(-2, HttpStatus.UNAUTHORIZED),
    NICKNAME_RULE_VIOLATION(-3, HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private HttpStatus httpStatus;

    DeclaredMemberResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
