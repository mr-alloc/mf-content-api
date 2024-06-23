package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class VerifyAccountReq {

    @Schema(description = "디바이스 타입 (0: PC, 1: MOBILE)")
    Integer deviceCode;


    /* 이메일 */
    @NotEmpty
    @Schema(description = "이메일")
    String email;

    /* 비밀번호 */
    @NotEmpty
    @Schema(description = "비밀번호")
    String password;
}
