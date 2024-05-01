package com.cofixer.mf.mfcontentapi.dto.res;

public record RejectFamilyRequestRes(
        Long id
) {

    public static RejectFamilyRequestRes of(Long familyId) {
        return new RejectFamilyRequestRes(familyId);
    }
}
