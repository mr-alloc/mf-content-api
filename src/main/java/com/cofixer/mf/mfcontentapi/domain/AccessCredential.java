package com.cofixer.mf.mfcontentapi.domain;

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

    @Column(name = "credential", nullable = false, length = 500)
    String credential;

    public static AccessCredential of(Long id, String credential) {
        AccessCredential verified = new AccessCredential();
        verified.accountId = id;
        verified.credential = credential;
        return verified;
    }
}
