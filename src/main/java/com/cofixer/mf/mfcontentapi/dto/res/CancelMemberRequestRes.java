package com.cofixer.mf.mfcontentapi.dto.res;

public record CancelMemberRequestRes(
        Long id
) {
    public static CancelMemberRequestRes of(Long memberId) {
        return new CancelMemberRequestRes(memberId);
    }
}
