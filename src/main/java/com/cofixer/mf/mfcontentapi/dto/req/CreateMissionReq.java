package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;

import java.util.Optional;
import java.util.function.Supplier;

public record CreateMissionReq(

        /* 미션명 */
        String name,

        Long categoryId,

        /* 미션 부제 */
        String subName,

        /* 미션 타입 */
        Integer type,

        /* 기한(초) */
        Optional<Long> deadline,

        ScheduleInfo scheduleInfo
) implements ValidatableRequest<CreateMissionReq> {

    @Override
    public CreateMissionReq validate(Supplier<CommonException> validateExceptionSupplier) {
        if (!ValidateUtil.isValidScheduleInfo(scheduleInfo)) {
            throw validateExceptionSupplier.get();
        }

        return this;
    }
}
