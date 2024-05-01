package com.cofixer.mf.mfcontentapi.dto.res;

public record AcceptFamilyRequestRes(
        Long id
) {
    public static AcceptFamilyRequestRes of(Long newFamilyId) {
        return new AcceptFamilyRequestRes(newFamilyId);
    }
}
