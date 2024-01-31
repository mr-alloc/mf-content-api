package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeclaredAccountResult {

    /**
     * -1: 이메일 생성규칙 위반
     * -2: 비밀번호 생성규칙 위반
     * -3: 중복된 이메일
     * -4: 계정을 찾을 수 없거나 일치하지 않은 정보
     */
    EMAIL_RULE_VIOLATION(-1),
    PASSWORD_RULE_VIOLATION(-2),
    DUPLICATED_EMAIL(-3),
    NOT_FOUND_ACCOUNT(-4);
    private final int code;

}
