package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.exception.CommonException;

import java.util.function.Supplier;

public interface ValidatableRequest<T> {

    T validate(Supplier<CommonException> validateExceptionSupplier);
}
