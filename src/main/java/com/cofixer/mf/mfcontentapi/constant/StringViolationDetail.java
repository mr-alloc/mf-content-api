package com.cofixer.mf.mfcontentapi.constant;

public interface StringViolationDetail {
    boolean satisfied(String value);

    String getViolateMessage();
}
