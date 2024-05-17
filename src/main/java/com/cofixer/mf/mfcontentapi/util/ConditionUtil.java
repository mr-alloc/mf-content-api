package com.cofixer.mf.mfcontentapi.util;

import com.cofixer.mf.mfcontentapi.exception.CommonException;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.function.Consumer;
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

    public static <T> T evaluate(boolean condition, T target, Consumer<T> decorator) {
        Objects.requireNonNull(target);
        if (condition) {
            decorator.accept(target);
        }
        return target;
    }
}
