package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.util.EpochUtil;

public record FamilyMemberSummary(
        Long id,

        String nickname,

        String profileImageUrl,

        Integer role,

        boolean isNewMember
) {

    public static FamilyMemberSummary of(FamilyMember familyMember, long now) {
        return new FamilyMemberSummary(
                familyMember.getMemberId(),
                familyMember.getNickname(),
                familyMember.getProfileImageUrl(),
                familyMember.getMemberRole(),
                EpochUtil.plusDays(familyMember.getRegisteredAt(), 3) >= now
        );
    }
}
