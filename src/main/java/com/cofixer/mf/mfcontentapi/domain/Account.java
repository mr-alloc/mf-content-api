package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_account", indexes = {
        @Index(name = "idx_email", columnList = "email", unique = true)
})
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 295493691792493645L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "phone")
    String phone;

    @Column(name = "verified_phone")
    Boolean verifiedPhone;

    @Column(name = "verified_email")
    Boolean verifiedEmail;

    @Column(name = "created_at", nullable = false)
    Long createdAt;

    public static Account forCreate(String email, String password) {
        Account newAccount = new Account();
        newAccount.email = email;
        newAccount.password = password;
        newAccount.createdAt = LocalDateTime.now().toEpochSecond(AppContext.APP_ZONE_OFFSET);
        return newAccount;
    }

    public void changeEncrypted(String encrypt) {
        this.password = encrypt;
    }
}
