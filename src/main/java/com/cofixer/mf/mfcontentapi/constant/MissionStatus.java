package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum MissionStatus {
    CREATED(0),
    IN_PROGRESS(1),
    COMPLETED(2),
    DELETED(3);

    private static final Map<Integer, MissionStatus> CACHED = CollectionUtil.arrayToMap(values(), MissionStatus::getCode);
    private final int code;

    public static boolean has(Integer status) {
        return CACHED.containsKey(status);
    }

    public static MissionStatus fromCode(Integer status) {
        return CACHED.get(status);
    }
}
