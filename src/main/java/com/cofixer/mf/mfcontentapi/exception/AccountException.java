package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import lombok.Getter;

import java.io.Serial;

@Getter
public class AccountException extends CommonException {
    @Serial
    private static final long serialVersionUID = -147351362771502273L;

    private final DeclaredAccountResult result;

    public AccountException(DeclaredAccountResult result) {
        super(result.name());
        this.result = result;
    }

    public AccountException(DeclaredAccountResult result, String message) {
        super(message);
        this.result = result;
    }

    @Override
    public int getCode() {
        return this.result.getCode();
    }
}
