package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Comment("계정 정보")
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


    @Comment("이메일")
    @Column(name = "email", nullable = false)
    String email;

    @Comment("비밀번호")
    @Column(name = "password", nullable = false)
    String password;

    @Comment("휴대폰 번호")
    @Column(name = "phone")
    String phone;

    @Comment("휴대폰 인증 여부")
    @Column(name = "verified_phone")
    Boolean verifiedPhone;

    @Comment("이메일 인증 여부")
    @Column(name = "verified_email")
    Boolean verifiedEmail;

    @Comment("계정 생성일시")
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    @Comment("마지막 로그인 일시")
    @Column(name = "last_signed_in_at")
    Long lastSignedInAt;

    public static Account forCreate(String email, String password) {
        Account newAccount = new Account();
        newAccount.email = email;
        newAccount.password = password;
        newAccount.createdAt = AppContext.APP_CLOCK.instant().getEpochSecond();
        return newAccount;
    }

    public void encryptPassword(String encrypt) {
        this.password = encrypt;
    }

    public void renewSignedInAt(Instant instant) {
        this.lastSignedInAt = instant.getEpochSecond();
    }
}
