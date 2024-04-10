package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ConditionUtil {
    public static void doIfTrue(boolean condition, Runnable runnable) {
        if (condition) {
            runnable.run();
        }
    }
}
