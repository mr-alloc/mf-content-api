package com.cofixer.mf.mfcontentapi.dto.res;

/**
 * 로그인(인증 성공)시 응답
 * @param credential 인증 토큰
 */

public record VerifiedAccountRes (
        String accessToken,

        String refreshToken
) {
}
