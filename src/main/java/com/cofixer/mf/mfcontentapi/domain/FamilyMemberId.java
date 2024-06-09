package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMemberId implements Serializable {
    @Serial
    private static final long serialVersionUID = 7209203449087964255L;

    @Comment("패밀리 ID")
    @Column(name = "family_id", nullable = false)
    Long familyId;

    @Comment("멤버 ID")
    @Column(name = "member_id", nullable = false)
    Long memberId;

    public boolean hasFamilyId() {
        return familyId != null;
    }

    public boolean hasMemberId() {
        return memberId != null;
    }

    public static FamilyMemberId of(Long familyId, Long memberId) {
        return new FamilyMemberId(familyId, memberId);
    }

    public static FamilyMemberId of(AuthorizedMember authorizedMember) {
        return new FamilyMemberId(authorizedMember.getFamilyId(), authorizedMember.getMemberId());
    }
}
