package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetAnniversaryRes(

        List<AnniversaryValue> anniversaries
) {
    public static GetAnniversaryRes of(List<AnniversaryValue> anniversaries) {
        return new GetAnniversaryRes(anniversaries);
    }
}
