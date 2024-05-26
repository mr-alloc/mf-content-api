package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.res.AnniversaryValue;


public record CreateAnniversaryRes(
        AnniversaryValue created
) {
    public static CreateAnniversaryRes of(AnniversaryValue anniversary) {
        return new CreateAnniversaryRes(anniversary);
    }
}
