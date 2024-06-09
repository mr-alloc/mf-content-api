package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.function.Supplier;

public record CreateFamilyMissionReq(

        @NotEmpty
        String name,

        /* 미션 부제 */
        String subName,

        Integer type,

        @Min(1)
        @NotNull
        Long assignee,

        ScheduleInfo scheduleInfo
) implements ValidatableRequest<CreateFamilyMissionReq> {

    @Override
    public CreateFamilyMissionReq validate(Supplier<CommonException> validateExceptionSupplier) {
        if (!ValidateUtil.isValidScheduleInfo(scheduleInfo)) {
            throw validateExceptionSupplier.get();
        }
        return this;
    }
}
