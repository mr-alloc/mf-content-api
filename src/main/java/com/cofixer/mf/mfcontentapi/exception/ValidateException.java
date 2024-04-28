package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.DeclaredValidateResult;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ValidateException extends CommonException {

    @Serial
    private static final long serialVersionUID = -7860108478252881956L;

    private final DeclaredValidateResult declaredResult;
    private String additionalMessage;


    public ValidateException(DeclaredValidateResult declaredResult) {
        super(declaredResult.toString());
        this.declaredResult = declaredResult;
    }

    public ValidateException(DeclaredValidateResult declaredResult, String additionalMessage) {
        super(additionalMessage);
        this.declaredResult = declaredResult;
        this.additionalMessage = additionalMessage;
    }

    @Override
    public int getCode() {
        return this.declaredResult.getCode();
    }
}
