package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Anniversary;

public record AnniversaryValue(
        Long id
) {
    public static AnniversaryValue of(Anniversary anniversary) {
        return new AnniversaryValue(
                anniversary.getId()
        );
    }
}
