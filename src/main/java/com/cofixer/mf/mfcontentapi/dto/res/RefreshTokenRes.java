package com.cofixer.mf.mfcontentapi.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 갱신 응답")
public record RefreshTokenRes(
        @Schema(description = "액세스 토큰")
        String accessToken
) {
    public static RefreshTokenRes of(String accessToken) {
        return new RefreshTokenRes(accessToken);
    }
}
