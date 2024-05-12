package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;
import com.cofixer.mf.mfcontentapi.domain.Member;

public record JoinRequestRes(
        Long memberId,

        String nickname,

        String profile,

        String introduce,

        Long requestedAt
) {
    public static JoinRequestRes of(FamilyMemberConnectRequest request, Member member) {
        return new JoinRequestRes(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                request.getIntroduce(),
                request.getRequestedAt()
        );
    }
}
