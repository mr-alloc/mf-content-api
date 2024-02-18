package com.cofixer.mf.mfcontentapi.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
public class ConfirmAccountReq {

    /* 이메일 */
    @NotEmpty
    String email;
}
