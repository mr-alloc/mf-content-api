package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

import static org.springframework.util.StringUtils.hasLength;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
@Table(name = "mf_family_member")
public class FamilyMember implements Serializable {
    @Serial
    private static final long serialVersionUID = -315493172654907568L;

    @Delegate
    @EmbeddedId
    FamilyMemberId id;

    @Column(name = "nickname", nullable = false)
    String nickname;

    @Column(name = "member_role", nullable = false)
    Integer memberRole;

    @Column(name = "profile_image_url")
    String profileImageUrl;

    @Column(name = "registered_at", nullable = false)
    Long registeredAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "family_id", insertable = false, updatable = false)
    Family family;

    public static FamilyMember forCreate(FamilyMemberId familyMemberId, MemberRoleType memberRoleType) {
        FamilyMember newer = new FamilyMember();

        newer.id = familyMemberId;
        newer.nickname = "";
        newer.memberRole = memberRoleType.getLevel();
        newer.registeredAt = AppContext.APP_CLOCK.instant().getEpochSecond();

        return newer;
    }

    public boolean hasNickName() {
        return hasLength(this.nickname);
    }

    public boolean nicknameIs(String nickname) {
        return this.nickname.equals(nickname);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
