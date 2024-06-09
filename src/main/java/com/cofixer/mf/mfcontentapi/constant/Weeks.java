package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public enum Weeks {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);


    private static final int START_BIT = 1;
    private static final Map<Integer, Weeks> CACHED_BY_BIT = CollectionUtil.toMap(List.of(values()), Weeks::getBit);
    private static final Map<Integer, Weeks> CACHED_BY_VALUE = CollectionUtil.toMap(List.of(values()), Weeks::getValue);
    private final int value;
    private final int bit;

    Weeks(int value) {
        this.value = START_BIT << (value - 1);
        this.bit = value;
    }


    public static Weeks fromBitPosition(int bitPosition) {
        return CACHED_BY_BIT.get(bitPosition);
    }

    public static Weeks fromValue(int value) {
        return CACHED_BY_VALUE.get(value);
    }

    public static List<Weeks> findWeeks(List<Integer> weekList) {
        //변경이 없는경우
        if (weekList == null) return null;
        //아무것도 선택 하지 않은경우
        if (weekList.isEmpty()) return List.of();

        int selected = weekList.stream()
                .map(Weeks::fromBitPosition)
                .filter(Objects::nonNull)
                .mapToInt(Weeks::getValue)
                .sum();
        return IntStream.range(0, Integer.SIZE).boxed()
                .map(i -> selected & (1 << i))
                .filter(bit -> bit != 0)
                .map(Weeks::fromValue)
                .filter(Objects::nonNull)
                .toList();
    }

    public static List<Integer> toBits(Integer selected) {
        if (selected == null || selected == 0) return List.of();

        return IntStream.range(0, Integer.SIZE).boxed()
                .map(i -> selected & (1 << i))
                .filter(bit -> bit != 0)
                .map(Weeks::fromValue)
                .map(Weeks::getBit)
                .toList();
    }

    public static int toSelected(Collection<Weeks> weeks) {
        return weeks.stream()
                .mapToInt(Weeks::getValue)
                .sum();
    }

    public static Integer toSelected(List<Integer> weekNumbers) {
        List<Weeks> weeks = findWeeks(weekNumbers);
        if (weeks == null || weeks.isEmpty()) return 0;

        return toSelected(weeks);
    }

    public static Integer toSelected(String separated) {
        List<Integer> weekNumbers = Arrays.stream(separated.split(","))
                .filter(StringUtils::hasLength)
                .map(Integer::parseInt).toList();

        return toSelected(weekNumbers);
    }
}
