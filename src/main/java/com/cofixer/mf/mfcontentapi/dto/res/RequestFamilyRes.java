package com.cofixer.mf.mfcontentapi.dto.res;

public record RequestFamilyRes(
        Long id
) {

    public static RequestFamilyRes of(Long familyId) {
        return new RequestFamilyRes(familyId);
    }
}
