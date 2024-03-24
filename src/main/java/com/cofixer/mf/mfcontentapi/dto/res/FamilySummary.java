package com.cofixer.mf.mfcontentapi.dto.res;

public record FamilySummary(
        Long familyId,
        String familyName,
        String familyDescription,
        Long registered
) {
}
