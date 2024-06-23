package com.cofixer.mf.mfcontentapi.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 갱신 요청")
public record RefreshTokenReq(

        @Schema(description = "디바이스 타입 (0: PC, 1: MOBILE)")
        Integer deviceCode,

        @Schema(description = "리프레시 토큰")
        String refreshToken,

        @Schema(description = "액세스 토큰")
        String accessToken
) {
}
