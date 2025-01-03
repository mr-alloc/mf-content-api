package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum MissionType {
    SCHEDULE(0),
    MISSION(1),
//    PACKAGE(2),
//    STEP(3),
//    TIME(4),
//    PROJECT(5),
//    SECRET(6),
//    SURPRISE(7),
//    FIX(8),
//    INTERVAL(9),
    ;

    private static final Map<Integer, MissionType> CACHED = CollectionUtil.toMap(List.of(MissionType.values()), MissionType::getValue);
    private final int value;

    public static boolean has(int type) {
        return CACHED.containsKey(type);
    }

    public static MissionType fromValue(Integer value) {
        return CACHED.get(value);
    }

    public static MissionType fromValue(Integer value, Supplier<CommonException> exceptionSupplier) {
        if (!has(value)) {
            throw exceptionSupplier.get();
        }

        return CACHED.get(value);
    }

    public boolean isSchedule() {
        return this == SCHEDULE;
    }
}
