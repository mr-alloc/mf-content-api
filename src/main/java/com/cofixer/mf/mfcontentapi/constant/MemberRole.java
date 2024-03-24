package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    MEMBER(0),
    SUB_MASTER(1),
    MASTER(2);

    private final int level;
}
