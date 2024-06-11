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
    WEEKLY(0),
    MONTHLY(1),
    YEARLY(2);

    private static final Map<Integer, RepeatOption> CACHED = CollectionUtil.toMap(List.of(values()), RepeatOption::getValue);
    private final Integer value;

    public static RepeatOption fromValue(Integer value) {
        return CACHED.get(value);
    }

    public boolean isWeek() {
        return this == WEEKLY;
    }

    public boolean equalsValue(Integer repeatOption) {
        return this.value.equals(repeatOption);
    }
}
