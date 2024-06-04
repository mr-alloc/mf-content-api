package com.cofixer.mf.mfcontentapi.dto.req;

import com.cofixer.mf.mfcontentapi.dto.res.AnniversaryValue;

import java.util.List;


public record CreateAnniversaryRes(
        List<AnniversaryValue> created
) {
    public static CreateAnniversaryRes of(List<AnniversaryValue> created) {
        return new CreateAnniversaryRes(created);
    }
}
