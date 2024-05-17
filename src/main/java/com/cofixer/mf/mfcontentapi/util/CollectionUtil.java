package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CollectionUtil {

    public static <T, V> Map<V, T> toMap(Collection<T> collection, Function<T, V> keyMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <T, V> List<V> convertList(Collection<T> collection, Function<T, V> extractor) {
        return collection.stream().map(extractor).toList();
    }

    public static <T, V> Map<V, T> arrayToMap(T[] types, Function<T, V> keyMapper) {
        return Arrays.stream(types).collect(Collectors.toMap(keyMapper, Function.identity()));
    }
}
