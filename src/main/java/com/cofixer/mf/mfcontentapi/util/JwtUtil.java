package com.cofixer.mf.mfcontentapi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JwtUtil {

    private static final Algorithm ALGORITHM = getAlgorithm();

    public static String createToken(long accountId, Instant currentInstant, long expireSecond) {
        try {
            return JWT.create()
                    .withIssuer(String.valueOf(accountId))
                    .withIssuedAt(currentInstant)
                    .withExpiresAt(currentInstant.plusSeconds(expireSecond))
                    .sign(ALGORITHM);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static DecodedJWT decode(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    public static DecodedJWT decodeWithoutExpiry(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .acceptExpiresAt(AppContext.REFRESH_TOKEN_EXPIRE_SECOND)
                .build();
        return verifier.verify(token);
    }

    private static Algorithm getAlgorithm() {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance(EncryptAlgorithm.RSA.getAlgorithm());
            instance.initialize(2048);
            KeyPair keyPair = instance.generateKeyPair();
            return Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException("Failed to create Algorithm");
        }
    }
}
