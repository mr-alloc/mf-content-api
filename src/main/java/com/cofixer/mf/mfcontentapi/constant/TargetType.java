package com.cofixer.mf.mfcontentapi.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetType {

    USER(0),
    FAMILY(1),
    ALL(2);

    private final Integer value;
}
