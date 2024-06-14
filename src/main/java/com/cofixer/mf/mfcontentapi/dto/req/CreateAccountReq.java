package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateAccountReq {

    /* 이메일 */
    @NotEmpty
    @Schema(description = "이메일")
    String email;

    /* 비밀번호 */
    @NotEmpty
    @Schema(description = "비밀번호")
    String password;
}
