package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum RepeatOption {
    NONE(-1),
    WEEKLY(1),
    MONTHLY(2),
    YEARLY(3);

    private static final Map<Integer, RepeatOption> CACHED = CollectionUtil.toMap(List.of(values()), RepeatOption::getValue);
    private final Integer value;

    public static RepeatOption fromValue(Integer value) {
        return CACHED.get(value);
    }
}
