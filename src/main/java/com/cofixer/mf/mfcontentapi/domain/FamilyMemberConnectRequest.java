package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_family_member_connect_request", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"family_id", "member_id", "connect_direction"})
})
public class FamilyMemberConnectRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 8163444913550263193L;

    @Delegate
    @EmbeddedId
    Id id;

    @Column(name = "introduce", nullable = false, length = 500)
    String introduce;

    @Column(name = "requested_at", nullable = false)
    Long requestedAt;

    public static FamilyMemberConnectRequest of(Id id, String introduce) {
        FamilyMemberConnectRequest newer = new FamilyMemberConnectRequest();

        newer.id = id;
        newer.introduce = introduce;
        newer.requestedAt = AppContext.APP_CLOCK.instant().getEpochSecond();

        return newer;
    }

    public FamilyMemberId getFamilyMemberId() {
        return FamilyMemberId.of(id.familyId, id.memberId);
    }


    @Getter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {
        @Serial
        private static final long serialVersionUID = 1670492750217983189L;

        @Column(name = "family_id", nullable = false)
        Long familyId;

        @Column(name = "member_id", nullable = false)
        Long memberId;

        //@see 1: FAMILY_TO_MEMBER, 2: MEMBER_TO_FAMILY
        @Column(name = "connect_direction", nullable = false)
        Integer connectDirection;

        public static Id of(FamilyMemberId familyMemberId, FamilyMemberDirection direction) {
            Id id = new Id();
            id.familyId = familyMemberId.getFamilyId();
            id.memberId = familyMemberId.getMemberId();
            id.connectDirection = direction.getValue();

            return id;
        }
    }
}
