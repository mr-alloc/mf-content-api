package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeclaredAccountResult implements DeclaredResult {

    /**
     * -1: 이메일 생성규칙 위반
     * -2: 비밀번호 생성규칙 위반
     * -3: 중복된 이메일
     * -4: 계정을 찾을 수 없거나 일치하지 않은 정보
     */
    EMAIL_RULE_VIOLATION(-1),
    PASSWORD_RULE_VIOLATION(-2),
    DUPLICATED_EMAIL(-3),
    NOT_FOUND_ACCOUNT(-4, HttpStatus.BAD_REQUEST),
    INVALID_DEVICE_TYPE(-5, HttpStatus.BAD_REQUEST),
    INVALID_ACCESS_TOKEN(-6, HttpStatus.BAD_REQUEST),
    EXPIRED_REFRESH_TOKEN(-7, HttpStatus.UNAUTHORIZED),
    NOT_PREFLIGHT_TESTER(-8, HttpStatus.UNAUTHORIZED);

    private final int code;
    private HttpStatus httpStatus;

    DeclaredAccountResult(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
