package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.exception.CommonException;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum StringViolation {
    JOIN_REQUEST_INTRODUCE(ByteLength.between(0, 255)),
    ;
    private final StringViolationDetail[] details;

    StringViolation(StringViolationDetail... details) {
        this.details = details;
    }

    public void inspect(String value, Function<String, CommonException> messageFunction) {
        for (StringViolationDetail detail : details) {
            if (!detail.satisfied(value)) {
                throw messageFunction.apply(detail.getViolateMessage());
            }
        }
    }

    public boolean inspect(String value, StringViolation violation) {
        for (StringViolationDetail detail : violation.getDetails()) {
            if (!detail.satisfied(value)) {
                return false;
            }
        }
        return true;
    }
}
