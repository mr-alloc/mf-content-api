package com.cofixer.mf.mfcontentapi.dto.req;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public class CreateMemberReq {
    String nickName;
}
