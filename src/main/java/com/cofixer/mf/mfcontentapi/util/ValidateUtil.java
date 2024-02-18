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
}
