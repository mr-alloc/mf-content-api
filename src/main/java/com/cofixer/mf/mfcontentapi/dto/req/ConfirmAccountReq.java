package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Schema(description = "계정확인 요청")
public record ConfirmAccountReq (

        /* 이메일 */
        @NotEmpty
        @Schema(description = "이메일")
        String email
) {

}
