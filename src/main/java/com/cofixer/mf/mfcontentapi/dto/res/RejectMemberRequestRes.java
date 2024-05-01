package com.cofixer.mf.mfcontentapi.dto.res;

public record RejectMemberRequestRes(
        Long id
) {

    public static RejectMemberRequestRes of(Long memberId) {
        return new RejectMemberRequestRes(memberId);
    }
}
