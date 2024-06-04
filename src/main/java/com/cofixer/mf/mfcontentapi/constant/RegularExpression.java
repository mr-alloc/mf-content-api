package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public enum RegularExpression {
    EMAIL("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"),
    PASSWORD("^(?=.*[0-9a-z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$"),
    NICKNAME("^[a-zA-Z0-9가-힣 ]{2,20}$"),
    FAMILY_NAME("^[a-zA-Z0-9가-힣 ]{2,20}$"),
    ;

    private final String value;
    private final Pattern pattern;

    RegularExpression(String value) {
        this.value = value;
        this.pattern = Pattern.compile(value);
    }
}
