package com.cofixer.mf.mfcontentapi.dto.res;

public record CancelFamilyRequestRes(
        Long id
) {
    public static CancelFamilyRequestRes of(Long familyId) {
        return new CancelFamilyRequestRes(familyId);
    }
}
