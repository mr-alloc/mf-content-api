package com.cofixer.mf.mfcontentapi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import lombok.NoArgsConstructor;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.function.Predicate;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JwtUtil {

    private static final Algorithm ALGORITHM = getAlgorithm();

    public static String createToken(long accountId) {
        try {
            return JWT.create()
                    .withIssuer(String.valueOf(accountId))
                    .withIssuedAt(AppContext.APP_CLOCK.instant())
                    .withExpiresAt(AppContext.APP_CLOCK.instant().plusSeconds(AppContext.CREDENTIAL_EXPIRE_SECOND))
                    .sign(ALGORITHM);
        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static DecodedJWT decode(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    public static boolean isValid(String token, Predicate<DecodedJWT> predicate) {
        try {
            DecodedJWT decodedJWT = decode(token);
            return predicate.test(decodedJWT);
        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static Algorithm getAlgorithm() {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance(EncryptAlgorithm.RSA.getAlgorithm());
            instance.initialize(2048);
            KeyPair keyPair = instance.generateKeyPair();
            return Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
