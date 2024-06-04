package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.exception.ValidateException;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ScheduleMode {

    // 1: 기간, 2: 다중
    SINGLE(1),
    MULTIPLE(2),
    PERIOD(3),
    REPEAT(4);
    private static final Map<Integer, ScheduleMode> CACHED = CollectionUtil.toMap(List.of(values()), ScheduleMode::getValue);
    private final Integer value;

    public static ScheduleMode fromValue(Integer value) {
        if (PERIOD.value.equals(value)) {
            return PERIOD;
        } else if (MULTIPLE.value.equals(value)) {
            return MULTIPLE;
        } else if (SINGLE.value.equals(value)) {
            return SINGLE;
        } else {
            throw new ValidateException(DeclaredValidateResult.FAILED_AT_COMMON_VALIDATION, "Invalid anniversary type value: " + value);
        }
    }
}
