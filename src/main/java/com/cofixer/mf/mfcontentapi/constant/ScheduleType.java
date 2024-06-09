package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleType {

    ANNIVERSARY(1),
    MISSION(2),
    ;

    private final Integer value;

}
