package com.cofixer.mf.mfcontentapi.dto.req;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateFamilyReq {

    String familyName;

    String familyDescription;
}
