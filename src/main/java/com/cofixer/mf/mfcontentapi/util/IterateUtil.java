package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static <T, V> Map<V, T> toMap(Collection<T> collection, Function<T, V> keyMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <T, V> List<V> convertList(Collection<T> collection, Function<T, V> extractor) {
        return collection.stream().map(extractor).toList();
    }
}
