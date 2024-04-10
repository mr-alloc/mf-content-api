package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.MemberRole;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

import static org.springframework.util.StringUtils.hasLength;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
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

    public static FamilyMember forCreate(Long familyId, Long memberId, MemberRole memberRole) {
        FamilyMemberId id = new FamilyMemberId(familyId, memberId);
        FamilyMember newer = new FamilyMember();

        newer.id = id;
        newer.nickname = "";
        newer.memberRole = memberRole.getLevel();
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


    @Getter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FamilyMemberId implements Serializable {
        @Serial
        private static final long serialVersionUID = 7209203449087964255L;

        @Column(name = "family_id", nullable = false)
        Long familyId;

        @Column(name = "member_id", nullable = false)
        Long memberId;

        public static FamilyMemberId of(Long familyId, Long memberId) {
            return new FamilyMemberId(familyId, memberId);
        }

        public static FamilyMemberId of(AuthorizedMember authorizedMember) {
            return new FamilyMemberId(authorizedMember.getFamilyId(), authorizedMember.getMemberId());
        }
    }
}
