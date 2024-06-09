package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
@Comment("일반 멤버 정보")
@Table(name = "mf_member", indexes = {
        @Index(name = "idx_aid", columnList = "aid", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_aid", columnNames = {"aid"}),
        @UniqueConstraint(name = "uk_nickname", columnNames = {"nickname"}),
})
public class Member implements Serializable {

    @Serial
    private static final long serialVersionUID = 7965544458786269019L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Comment("계정 ID")
    @Column(name = "aid")
    Long accountId;

    @Comment("역할 [0: 게스트, 1: 일반회원, 2: 관리자]")
    @Column(name = "role")
    Integer role;

    @Comment("프로필 이미지 URL")
    @Column(name = "profile_image_url")
    String profileImageUrl;

    @Comment("닉네임")
    @Column(name = "nickname")
    String nickname;

    @Comment("이용 정지 여부")
    @Column(name = "is_blocked")
    boolean isBlocked;

    @Comment("가입일시")
    @Column(name = "registered_at")
    Long registeredAt;

    public static Member forCreate(Long accountId) {
        Member newer = new Member();
        newer.accountId = accountId;
        newer.role = AccountRoleType.MEMBER.getLevel();
        newer.isBlocked = false;

        return newer;
    }

    public AccountRoleType getMemberRole() {
        return AccountRoleType.exist(role)
                ? AccountRoleType.of(role)
                : AccountRoleType.MEMBER;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
        this.registeredAt = AppContext.APP_CLOCK.instant().getEpochSecond();
    }


    public boolean hasNickName() {
        return StringUtils.hasLength(this.nickname);
    }

    public boolean nicknameIs(String nickname) {
        return this.nickname.equals(nickname);
    }
}
