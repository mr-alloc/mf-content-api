package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.exception.CommonException;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.function.Supplier;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateFamilyMissionReq implements ValidatableRequest<CreateFamilyMissionReq> {

    @NotEmpty
    String title;

    @Min(1)
    @NotNull
    Long assignee;

    Integer type;

    Long startTime;

    Long endTime;


    @Override
    public CreateFamilyMissionReq validate(Supplier<CommonException> validateExceptionSupplier) {
        boolean existType = MissionType.has(this.type);
        boolean validTimestamp = ValidateUtil.isValidStampRange(this.startTime, this.endTime);

        if (!existType || !validTimestamp) {
            throw validateExceptionSupplier.get();
        }

        return this;
    }
}
