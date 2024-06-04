package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import com.cofixer.mf.mfcontentapi.exception.ValidateException;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;

import static com.cofixer.mf.mfcontentapi.constant.DeclaredValidateResult.FAILED_AT_COMMON_VALIDATION;

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

    public static Matcher assertValidGetMatcher(String value, RegularExpression regularExpression) {
        if (!isValid(value, regularExpression)) {
            throw new ValidateException(FAILED_AT_COMMON_VALIDATION);
        }

        Matcher matcher = regularExpression.getPattern().matcher(value);
        if (!matcher.matches()) {
            throw new ValidateException(FAILED_AT_COMMON_VALIDATION);
        }
        return matcher;
    }
}
