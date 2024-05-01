package com.cofixer.mf.mfcontentapi.dto.res;

public record AcceptMemberRequestRes(
        Long id
) {
    public static AcceptMemberRequestRes of(Long newMemberId) {
        return new AcceptMemberRequestRes(newMemberId);
    }
}
