package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.AnniversaryType;
import com.cofixer.mf.mfcontentapi.constant.Days;
import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;

import java.util.List;
import java.util.function.Supplier;

public record CreateAnniversaryReq(
        String name,
        Integer type,
        String yearMonth,
        List<Integer> days,
        Long startAt,
        Long endAt
) implements ValidatableRequest<CreateAnniversaryReq> {

    @Override
    public CreateAnniversaryReq validate(Supplier<CommonException> validateExceptionSupplier) {
        boolean isValid = switch (AnniversaryType.fromValue(type)) {
            case PERIOD -> ValidateUtil.isValidStampRange(startAt, endAt);
            case SINGLE, MULTIPLE -> {
                boolean validYearMonth = ValidateUtil.isValid(yearMonth, RegularExpression.YEAR_MONTH);
                boolean validLength = Days.values().length >= days.size();
                yield validYearMonth && validLength;
            }
        };

        if (!isValid) {
            throw validateExceptionSupplier.get();
        }

        return this;
    }
}
