package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Family;

public record GetFamilyInfoRes(
        Long id,

        String inviteCode,
        String name
) {
    public static GetFamilyInfoRes of(Family family) {
        return new GetFamilyInfoRes(
                family.getId(),
                family.getInviteCode(),
                family.getName()
        );
    }
}
