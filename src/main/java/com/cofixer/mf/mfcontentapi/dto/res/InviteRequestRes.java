package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;

public record InviteRequestRes(
        Long familyId,

        String name,

        String logo,

        Long requestedAt
) {
    public static InviteRequestRes of(FamilyMemberConnectRequest connectRequest, Family family) {
        return new InviteRequestRes(
                family.getId(),
                family.getName(),
                family.getLogoImageUrl(),
                connectRequest.getRequestedAt()
        );
    }
}
