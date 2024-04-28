package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMember;

public record FamilyMemberSummary(
        Long id,

        String nickname,

        String profileImageUrl,

        Integer role
) {

    public static FamilyMemberSummary of(FamilyMember familyMember) {
        return new FamilyMemberSummary(
                familyMember.getMemberId(),
                familyMember.getNickname(),
                familyMember.getProfileImageUrl(),
                familyMember.getMemberRole()
        );
    }
}
