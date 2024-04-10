package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.UserProtocol;
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
    AccountRoleType role;

    public static AuthorizedMember of(Long accountId, Member member, Long familyId) {
        AuthorizedMember newer = new AuthorizedMember();
        newer.accountId = accountId;
        newer.memberId = member.getId();
        newer.familyId = familyId;
        newer.role = member.getMemberRole();
        return newer;
    }

    public boolean hasMemberId() {
        return memberId != null;
    }

    public boolean forFamilyMember() {
        return familyId != null && !familyId.equals(UserProtocol.NOT_SELECTED_FAMILY_ID);
    }

}
