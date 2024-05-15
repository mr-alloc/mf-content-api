package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionStatus {
    CREATED(0),
    PROGRESS(1),
    COMPLETED(2),
    PAUSED(3);
    private final int code;
}
