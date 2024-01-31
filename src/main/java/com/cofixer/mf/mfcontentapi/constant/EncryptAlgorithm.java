package com.cofixer.mf.mfcontentapi.constant;

public enum EncryptAlgorithm {
    SHA256("SHA-256"),
    RSA("RSA");

    private String algorithm;

    EncryptAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
