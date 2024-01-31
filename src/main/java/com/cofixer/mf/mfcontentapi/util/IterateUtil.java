package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.function.Predicate;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class IterateUtil {

    public static <T> T find(Iterable<T> iterable, Predicate<T> findCondition) {
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (findCondition.test(next)) {
                return next;
            }
        }

        return null;
    }
}
