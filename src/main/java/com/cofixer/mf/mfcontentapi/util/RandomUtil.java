package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.function.IntPredicate;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RandomUtil {

    private static final int ASCII_NUMBER_START = 48;
    private static final int ASCII_NUMBER_END = 57;
    private static final int ASCII_LOWER_START = 97;
    private static final int ASCII_LOWER_F = 102;
    private static final int ASCII_LOWER_END = 122;
    private static final int HEX_COLOR_LENGTH = 6;

    private static final IntPredicate NUMBER_RANGE = i -> ASCII_NUMBER_START <= i && i <= ASCII_NUMBER_END;
    private static final IntPredicate LOWER_CHARACTER_RANGE = i -> ASCII_LOWER_START <= i && i <= ASCII_LOWER_END;
    private static final IntPredicate LOWER_HAX_CHARACTER_RANGE = i -> ASCII_LOWER_START <= i && i <= ASCII_LOWER_F;

    private static final IntPredicate HEX_COLOR_RANGE = i -> NUMBER_RANGE.test(i) || LOWER_HAX_CHARACTER_RANGE.test(i);

    private static final Random RANDOM = new Random();

    public static String generateRandomHexColor() {
        return RANDOM.ints(ASCII_NUMBER_START, ASCII_LOWER_F + 1)
                .filter(HEX_COLOR_RANGE)
                .limit(HEX_COLOR_LENGTH)
                .collect(() -> new StringBuilder("#"), StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
