package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Entity
@DynamicUpdate
@Table(name = "mf_member", indexes = {
        @Index(name = "idx_aid", columnList = "aid", unique = true)
})
public class Member implements Serializable {

    @Serial
    private static final long serialVersionUID = 7965544458786269019L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    /* mf_account.id */
    @Column(name = "aid")
    Long accountId;

    @Column(name = "role")
    Integer role;

    @Column(name = "profile_image_url")
    String profileImageUrl;

    @Column(name = "nickname")
    String nickname;

    @Column(name = "registered_at")
    Long registeredAt;

    public static Member forCreate(Long accountId) {
        Member newer = new Member();
        newer.accountId = accountId;
        newer.role = AccountRoleType.MEMBER.getLevel();

        return newer;
    }

    public AccountRoleType getMemberRole() {
        return AccountRoleType.exist(role)
                ? AccountRoleType.of(role)
                : AccountRoleType.MEMBER;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
        this.registeredAt = LocalDateTime.now().toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }


    public boolean hasNickName() {
        return StringUtils.hasLength(this.nickname);
    }

    public boolean nicknameIs(String nickname) {
        return this.nickname.equals(nickname);
    }
}
