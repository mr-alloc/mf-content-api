package com.cofixer.mf.mfcontentapi.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateFamilyMissionReq {

    @NotEmpty
    String name;

    /* 미션 부제 */
    String subName;

    @Min(1)
    @NotNull
    Long assignee;

    Integer type;

    /* 미션 시작일 */
    Long startDueStamp;

    /* 미션 종료일 */
    Long endDueStamp;
}
