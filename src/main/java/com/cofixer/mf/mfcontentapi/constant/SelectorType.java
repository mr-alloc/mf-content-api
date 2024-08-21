package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelectorType {

    USER(0),
    FAMILY(1)
    ;

    private final Integer value;
}
