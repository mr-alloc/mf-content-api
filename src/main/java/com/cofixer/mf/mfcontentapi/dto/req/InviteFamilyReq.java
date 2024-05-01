package com.cofixer.mf.mfcontentapi.dto.req;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InviteFamilyReq {

    Long memberId;
}
