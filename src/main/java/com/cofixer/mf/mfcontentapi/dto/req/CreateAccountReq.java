package com.cofixer.mf.mfcontentapi.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateAccountReq {

    /* 이름 */
    @NotEmpty
    String email;

    /* 비밀번호 */
    @NotEmpty
    String password;
}
