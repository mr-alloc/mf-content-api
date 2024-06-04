package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.RepeatOption;
import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;

import java.time.DayOfWeek;
import java.util.function.Supplier;

public record CreateAnniversaryReq(
        String name,
        ScheduleInfo scheduleInfo
) implements ValidatableRequest<CreateAnniversaryReq> {

    @Override
    public CreateAnniversaryReq validate(Supplier<CommonException> validateExceptionSupplier) {
        boolean isValid = switch (ScheduleMode.fromValue(scheduleInfo.scheduleMode())) {
            case PERIOD -> ValidateUtil.isValidStampRange(scheduleInfo.startAt(), scheduleInfo.endAt());
            case SINGLE, MULTIPLE -> scheduleInfo.selected().isEmpty();
            case REPEAT -> {
                boolean isValidRepeatOption = switch (RepeatOption.fromValue(scheduleInfo.repeatOption())) {
                    case WEEKLY -> DayOfWeek.of(scheduleInfo.repeatValue().intValue()) != null;
                    case MONTHLY, YEARLY -> scheduleInfo.repeatValue() > 0;
                    default -> false;
                };

                yield isValidRepeatOption && ValidateUtil.isValidStampRange(scheduleInfo.startAt(), scheduleInfo.endAt());
            }
        };

        if (!isValid) {
            throw validateExceptionSupplier.get();
        }

        return this;
    }
}
