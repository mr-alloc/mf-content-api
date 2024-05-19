package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class ObjectUtil {

    public static boolean notEquals(Object obj1, Object obj2) {
        return !equals(obj1, obj2);
    }

    public static boolean equals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }
}
