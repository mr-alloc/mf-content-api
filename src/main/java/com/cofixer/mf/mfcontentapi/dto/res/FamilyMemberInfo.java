package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMember;

public record FamilyMemberInfo(

        Long id,
        String nickname,
        Integer role,
        String profile
) {
    public static FamilyMemberInfo of(FamilyMember familyMember) {
        return new FamilyMemberInfo(
                familyMember.getFamilyId(),
                familyMember.getNickname(),
                familyMember.getMemberRole(),
                familyMember.getProfileImageUrl()
        );
    }
}
