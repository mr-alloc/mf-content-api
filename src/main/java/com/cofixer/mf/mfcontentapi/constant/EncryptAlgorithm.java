package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptAlgorithm {
    MD5("MD5"),
    SHA224("SHA-224"),
    SHA256("SHA-256"),
    RSA("RSA");

    private final String algorithm;
}
