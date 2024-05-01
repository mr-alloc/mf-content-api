package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetFamilyConnectRequestRes(
        List<FamilyConnectRequestRes> familyRequests
) {
    public static GetFamilyConnectRequestRes of(List<FamilyConnectRequestRes> connectRequests) {
        return new GetFamilyConnectRequestRes(connectRequests);
    }
}
