package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {

    ASSIGN_FAMILY_MISSION(1),
    START_FAMILY_MISSION(2),
    COMPLETE_FAMILY_MISSION(3);

    private final Integer value;
}
