package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.Member;

public record MemberDetailRes(

        Long id,

        String profile,

        String nickname,

        String email,

        long registeredAt,

        long lastSignedInAt
) {
    public static MemberDetailRes of(Account account, Member member) {
        return new MemberDetailRes(
                member.getId(),
                member.getProfileImageUrl(),
                member.getNickname(),
                account.getEmail(),
                account.getCreatedAt(),
                account.getLastSignedInAt()
        );
    }

    public static MemberDetailRes of(Account found, FamilyMember familyMember) {
        return new MemberDetailRes(
                found.getId(),
                familyMember.getProfileImageUrl(),
                familyMember.getNickname(),
                found.getEmail(),
                found.getCreatedAt(),
                found.getLastSignedInAt()
        );
    }
}
