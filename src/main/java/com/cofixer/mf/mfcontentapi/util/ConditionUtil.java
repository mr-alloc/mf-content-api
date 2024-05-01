package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.exception.CommonException;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ConditionUtil {
    public static void doIfTrue(boolean condition, Runnable runnable) {
        if (condition) {
            runnable.run();
        }
    }

    public static void throwIfTrue(boolean condition, Supplier<CommonException> exceptionSupplier) {
        if (condition) {
            throw exceptionSupplier.get();
        }
    }
}
