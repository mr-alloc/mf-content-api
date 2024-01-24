package com.cofixer.mf.mfcontentapi.dto.req;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateMissionReq {
    /* 미션명 */
    String missionName;

    /* 수행자 */
    Long assignee;

    /* 미션 타입 */
    Integer missionType;

    /* 기한(일 단위) */
    Integer deadLine;
}
