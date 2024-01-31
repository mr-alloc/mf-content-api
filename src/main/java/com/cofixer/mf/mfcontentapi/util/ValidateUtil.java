package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ValidateUtil {

    public static boolean isValid(String email, RegularExpression regularExpression) {
        return email == null || email.matches(regularExpression.getValue());
    }

    public static boolean nonValid(String email, RegularExpression regularExpression) {
        return !isValid(email, regularExpression);
    }
}
