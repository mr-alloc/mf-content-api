package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.constant.StringViolation;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class StringUtil {

    public static boolean validate(String value, StringViolation violation) {
        Objects.requireNonNull(value);
        return violation.inspect(value, violation);
    }

    public static void validate(String value, StringViolation violation, Function<String, CommonException> messageFunction) {
        Objects.requireNonNull(value);
        violation.inspect(value, messageFunction);
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean isNotNull(String value) {
        return value != null;
    }
}
