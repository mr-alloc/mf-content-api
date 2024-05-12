package com.cofixer.mf.mfcontentapi.constant;


import java.nio.charset.StandardCharsets;

public record ByteLength(
        int min,
        int max
) implements StringViolationDetail {

    public static ByteLength between(int min, int max) {
        return new ByteLength(min, max);
    }

    @Override
    public boolean satisfied(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        return bytes.length >= min && bytes.length <= max;
    }

    @Override
    public String getViolateMessage() {
        return String.format("Byte length must be between %d and %d", min, max);
    }
}
