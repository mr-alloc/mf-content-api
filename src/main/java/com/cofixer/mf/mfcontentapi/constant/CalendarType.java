package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarType {

    SOLAR(1),
    LUNAR(2),
    ;

    private final int value;
}
