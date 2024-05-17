package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetOwnFamiliesRes(
        List<FamilySummary> ownFamilies
) {
    public static GetOwnFamiliesRes of(List<FamilySummary> familySummaries) {
        return new GetOwnFamiliesRes(familySummaries);
    }
}
