package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.exception.ValidateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnniversaryType {

    // 1: 기간, 2: 다중
    PERIOD(1),
    SINGLE(2),
    MULTIPLE(3);

    private final Integer value;

    public static AnniversaryType fromValue(Integer value) {
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
