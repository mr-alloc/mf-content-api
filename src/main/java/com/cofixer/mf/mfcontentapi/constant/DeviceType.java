package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.exception.AccountException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeviceType {

    PC(0),
    MOBILE(1);


    private final int code;

    public static DeviceType fromCode(Integer code) {
        if (code == null) {
            throw new AccountException(DeclaredAccountResult.INVALID_DEVICE_TYPE);
        }

        for (DeviceType deviceType : values()) {
            if (deviceType.code == code) {
                return deviceType;
            }
        }

        throw new AccountException(DeclaredAccountResult.INVALID_DEVICE_TYPE);
    }
}
