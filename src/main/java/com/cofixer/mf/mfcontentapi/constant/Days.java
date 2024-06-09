package com.cofixer.mf.mfcontentapi.constant;


import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

@Getter
public enum Days {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8),
    NINTH(9),
    TENTH(10),
    ELEVENTH(11),
    TWELFTH(12),
    THIRTEENTH(13),
    FOURTEENTH(14),
    FIFTEENTH(15),
    SIXTEENTH(16),
    SEVENTEENTH(17),
    EIGHTEENTH(18),
    NINETEENTH(19),
    TWENTIETH(20),
    TWENTY_FIRST(21),
    TWENTY_SECOND(22),
    TWENTY_THIRD(23),
    TWENTY_FOURTH(24),
    TWENTY_FIFTH(25),
    TWENTY_SIXTH(26),
    TWENTY_SEVENTH(27),
    TWENTY_EIGHTH(28),
    TWENTY_NINTH(29),
    THIRTIETH(30),
    THIRTY_FIRST(31);
    //비트의 10진수 값 (3: 4)
    private final Integer value;
    //태그 비트 위치 (3: 3번째 비트)
    private final int bit;
    private static final int START_BIT = 1;
    private static final Map<Integer, Days> CACHED_BY_BIT = CollectionUtil.toMap(List.of(values()), Days::getBit);
    private static final Map<Integer, Days> CACHED_BY_VALUE = CollectionUtil.toMap(List.of(values()), Days::getValue);


    Days(int value) {
        this.value = START_BIT << (value - 1);
        this.bit = value;
    }


    public static Days fromBitPosition(int bitPosition) {
        return CACHED_BY_BIT.get(bitPosition);
    }

    public static Days fromValue(int value) {
        return CACHED_BY_VALUE.get(value);
    }

    public static List<Days> findDays(List<Integer> dayList) {
        //변경이 없는경우
        if (dayList == null) return null;
        //아무것도 선택 하지 않은경우
        if (dayList.isEmpty()) return List.of();

        int selected = dayList.stream()
                .map(Days::fromBitPosition)
                .filter(Objects::nonNull)
                .mapToInt(Days::getValue)
                .sum();
        return IntStream.range(0, Integer.SIZE).boxed()
                .map(i -> selected & (1 << i))
                .filter(bit -> bit != 0)
                .map(Days::fromValue)
                .filter(Objects::nonNull)
                .toList();
    }

    public static List<Integer> toBits(Integer selected) {
        if (selected == null || selected == 0) return List.of();

        return IntStream.range(0, Integer.SIZE).boxed()
                .map(i -> selected & (1 << i))
                .filter(bit -> bit != 0)
                .map(Days::fromValue)
                .map(Days::getBit)
                .toList();
    }

    public static int toSelected(Collection<Days> days) {
        return days.stream()
                .mapToInt(Days::getValue)
                .sum();
    }

    public static Integer toSelected(List<Integer> dayNumbers) {
        List<Days> days = findDays(dayNumbers);
        if (days == null || days.isEmpty()) return 0;

        return toSelected(days);
    }

    public static Integer toSelected(String separated) {
        List<Integer> dayNumbers = Arrays.stream(separated.split(","))
                .filter(StringUtils::hasLength)
                .map(Integer::parseInt).toList();

        return toSelected(dayNumbers);
    }

}
