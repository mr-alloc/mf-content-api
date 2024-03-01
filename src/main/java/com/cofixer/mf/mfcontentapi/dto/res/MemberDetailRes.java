package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.domain.Member;

public record MemberDetailRes(

        String nickname,
        String email,

        long registeredAt,

        long lastSignedInAt
) {
    public static MemberDetailRes of(Account account, Member member) {
        return new MemberDetailRes(
                member.getNickname(),
                account.getEmail(),
                account.getCreatedAt(),
                account.getLastSignedInAt()
        );
    }
}
