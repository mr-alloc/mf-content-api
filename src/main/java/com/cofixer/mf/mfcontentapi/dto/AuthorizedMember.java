package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.constant.UserProtocol;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
public class AuthorizedMember {

    Long accountId;
    Long memberId;
    Long familyId;
    AccountRoleType accountRole;
    MemberRoleType memberRole;

    public static AuthorizedMember of(Long accountId, Member member, Optional<FamilyMember> mayFamilyMember) {
        AuthorizedMember newer = new AuthorizedMember();
        newer.accountId = accountId;
        newer.memberId = member.getId();
        newer.accountRole = member.getMemberRole();

        newer.familyId = UserProtocol.NOT_SELECTED_FAMILY_ID;
        newer.memberRole = MemberRoleType.NONE;

        mayFamilyMember.ifPresent(familyMember -> {
            newer.familyId = familyMember.getFamilyId();
            newer.memberRole = MemberRoleType.fromLevel(familyMember.getMemberRole());
        });

        return newer;
    }

    public boolean hasMemberId() {
        return memberId != null;
    }

    public boolean forFamilyMember() {
        return familyId != null && !familyId.equals(UserProtocol.NOT_SELECTED_FAMILY_ID);
    }

}
