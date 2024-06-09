package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;

import java.util.function.Supplier;

public record CreateAnniversaryReq(
        String name,
        ScheduleInfo scheduleInfo
) implements ValidatableRequest<CreateAnniversaryReq> {

    @Override
    public CreateAnniversaryReq validate(Supplier<CommonException> validateExceptionSupplier) {
        if (!ValidateUtil.isValidScheduleInfo(scheduleInfo)) {
            throw validateExceptionSupplier.get();
        }

        return this;
    }
}
