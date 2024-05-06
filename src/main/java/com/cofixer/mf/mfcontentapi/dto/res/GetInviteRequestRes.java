package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetInviteRequestRes(
        List<InviteRequestRes> familyRequests
) {
    public static GetInviteRequestRes of(List<InviteRequestRes> connectRequests) {
        return new GetInviteRequestRes(connectRequests);
    }
}
