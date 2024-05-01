package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetMemberConnectRequestRes(
        List<MemberConnectRequestRes> memberRequests
) {
    public static GetMemberConnectRequestRes of(List<MemberConnectRequestRes> connectRequests) {
        return new GetMemberConnectRequestRes(connectRequests);
    }
}
