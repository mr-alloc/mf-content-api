package com.cofixer.mf.mfcontentapi.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateMissionReq {
    /* 미션명 */
    @NotEmpty
    String name;

    /* 미션 부제 */
    String subName;

    /* 미션 타입 */
    Integer type;

    /* 미션 시작일(utc) */
    Long startDate;

    /* 기한(초) */
    Long deadline;
}
