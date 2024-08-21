package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public record CreateFamilyMissionReq(

        @NotEmpty
        String name,

        /* 미션 부제 */
        String subName,

        Long categoryId,

        Integer type,

        /* 기한(초) */
        Optional<Long> deadline,

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

    public Long getAssigneeSafe(long defaultAssignee) {
        return Optional.of(assignee)
                .filter(id -> id != 0)
                .orElse(defaultAssignee);
    }
}
