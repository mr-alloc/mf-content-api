package com.cofixer.mf.mfcontentapi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptAlgorithm {
    SHA256("SHA-256"),
    RSA("RSA");

    private final String algorithm;
}
