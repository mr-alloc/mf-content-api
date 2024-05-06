package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Family;

public record GetFamilyInfoRes(
        Long id,

        String inviteCode
) {
    public static GetFamilyInfoRes of(Family family) {
        return new GetFamilyInfoRes(family.getId(), family.getInviteCode());
    }
}
