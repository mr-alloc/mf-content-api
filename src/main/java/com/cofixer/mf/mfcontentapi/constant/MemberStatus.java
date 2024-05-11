package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
    BLOCKED("BLOCKED"),
    ;

    private final String code;
}
