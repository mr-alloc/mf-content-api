package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.DeclaredValidateResult;
import lombok.Getter;

import java.io.Serial;
import java.util.function.Supplier;

@Getter
public class ValidateException extends CommonException {

    @Serial
    private static final long serialVersionUID = -7860108478252881956L;
    public static Supplier<ValidateException> DEFAULT_SUPPLIER = () ->
            new ValidateException(DeclaredValidateResult.FAILED_AT_COMMON_VALIDATION);

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
