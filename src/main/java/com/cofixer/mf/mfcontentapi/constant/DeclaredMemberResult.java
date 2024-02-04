package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeclaredMemberResult {

    /**
     * -1: 회원을 찾을 수 없음
     */
    NOT_FOUND_MEMBER(-1),
    NOT_AUTHORIZED_ROLE(-2);
    private final int code;
}
