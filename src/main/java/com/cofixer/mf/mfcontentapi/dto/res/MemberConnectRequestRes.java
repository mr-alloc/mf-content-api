package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;
import com.cofixer.mf.mfcontentapi.domain.Member;

public record MemberConnectRequestRes(
        Long memberId,

        String nickname,

        String profile,

        Long requestedAt
) {
    public static MemberConnectRequestRes of(FamilyMemberConnectRequest request, Member member) {
        return new MemberConnectRequestRes(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                request.getRequestedAt()
        );
    }
}
