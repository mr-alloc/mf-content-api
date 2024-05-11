package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.constant.UserProtocol;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
public class AuthorizedMember {

    Long accountId;
    Long memberId;
    Long familyId;
    AccountRoleType accountRole;
    MemberRoleType memberRole;
    boolean isBlocked;

    public static AuthorizedMember of(Long accountId, Member member) {
        AuthorizedMember newer = new AuthorizedMember();
        newer.accountId = accountId;
        newer.memberId = member.getId();
        newer.accountRole = member.getMemberRole();

        newer.familyId = UserProtocol.NOT_SELECTED_FAMILY_ID;
        newer.memberRole = MemberRoleType.NONE;
        newer.isBlocked = member.isBlocked();

        return newer;
    }

    public void decorateFamilyMember(FamilyMember familyMember) {
        this.familyId = familyMember.getFamilyId();
        this.memberRole = MemberRoleType.fromLevel(familyMember.getMemberRole());
    }

    public boolean hasMemberId() {
        return memberId != null;
    }

    public boolean forFamilyMember() {
        return familyId != null && !familyId.equals(UserProtocol.NOT_SELECTED_FAMILY_ID);
    }

}
