package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;

public record FamilyConnectRequestRes(
        Long id,

        String name,

        String logo,

        Long requestedAt
) {
    public static FamilyConnectRequestRes of(FamilyMemberConnectRequest connectRequest, Family family) {
        return new FamilyConnectRequestRes(
                family.getId(),
                family.getName(),
                family.getLogoImageUrl(),
                connectRequest.getRequestedAt()
        );
    }
}
