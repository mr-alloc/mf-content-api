package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Family;

public record RequestFamilyRes(
        Long id,

        String familyName
) {

    public static RequestFamilyRes of(Family requestedFamily) {
        return new RequestFamilyRes(requestedFamily.getId(), requestedFamily.getName());
    }
}
