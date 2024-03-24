package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetFamilyRes(
        List<FamilySummary> families
) {
    public static GetFamilyRes of(List<FamilySummary> familySummaries) {
        return new GetFamilyRes(familySummaries);
    }
}
