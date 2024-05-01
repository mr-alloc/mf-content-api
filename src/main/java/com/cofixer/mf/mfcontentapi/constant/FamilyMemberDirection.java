package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FamilyMemberDirection {
    FAMILY_TO_MEMBER(1),
    MEMBER_TO_FAMILY(2);

    private final Integer value;
}
