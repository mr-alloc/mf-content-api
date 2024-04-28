package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ValidateUtil {

    public static boolean isValid(String str, RegularExpression regularExpression) {
        return str == null || str.matches(regularExpression.getValue());
    }

    public static boolean nonValid(String str, RegularExpression regularExpression) {
        return !isValid(str, regularExpression);
    }

    public static boolean isValidStampRange(Long startTime, Long endTime) {
        // 둘다 있을때만 대소비교
        if (startTime != null && endTime != null) {
            return startTime < endTime;
        }

        return true;
    }
}
