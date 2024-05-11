package com.cofixer.mf.mfcontentapi.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EpochUtil {
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;
    private static final long SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;
    private static final long SECONDS_PER_WEEK = 7 * SECONDS_PER_DAY;

    public static long plusSeconds(long epoch, long seconds) {
        return Math.addExact(epoch, seconds);
    }

    public static long plusMinutes(long epoch, long minutes) {
        return Math.addExact(epoch, Math.multiplyExact(minutes, SECONDS_PER_MINUTE));
    }

    public static long plusHours(long epoch, long hours) {
        return Math.addExact(epoch, Math.multiplyExact(hours, SECONDS_PER_HOUR));
    }

    public static long plusDays(long epoch, long days) {
        return Math.addExact(epoch, Math.multiplyExact(days, SECONDS_PER_DAY));
    }
}
