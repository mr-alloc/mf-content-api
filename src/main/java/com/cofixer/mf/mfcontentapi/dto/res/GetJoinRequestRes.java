package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetJoinRequestRes(
        List<JoinRequestRes> joinRequests
) {
    public static GetJoinRequestRes of(List<JoinRequestRes> connectRequests) {
        return new GetJoinRequestRes(connectRequests);
    }
}
