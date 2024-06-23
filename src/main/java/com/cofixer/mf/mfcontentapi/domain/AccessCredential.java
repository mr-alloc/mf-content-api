package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "mf_access_credentials", indexes = {
        @Index(name = "idx_credential", columnList = "credential", unique = true)
})
public class AccessCredential implements Serializable {
    @Serial
    private static final long serialVersionUID = -7868011931010365835L;

    /* mf_account.id */
    @Id
    @Column(name = "account_id")
    Long accountId;

    @Column(name = "device_type", nullable = false)
    Integer deviceType;

    @Column(name = "credential", nullable = false, length = 500)
    String credential;

    @Column(name = "latest_access_token", nullable = false, length = 500)
    String latestAccessToken;

    public static AccessCredential of(Long id, DeviceType deviceType, String credential, String latestAccessToken) {
        AccessCredential verified = new AccessCredential();
        verified.accountId = id;
        verified.credential = credential;
        verified.deviceType = deviceType.getCode();
        verified.latestAccessToken = latestAccessToken;
        return verified;
    }

    public boolean isNotEqualsAccessToken(String latestAccessToken) {
        return !this.latestAccessToken.equals(latestAccessToken);
    }

    public void updateAccessToken(String newAccessToken) {
        this.latestAccessToken = newAccessToken;
    }
}
