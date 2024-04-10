package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.Member;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SimpleMemberInfoRes {

    Long id;

    String nickname;

    Integer role;

    String profileImageUrl;

    public static SimpleMemberInfoRes of(Member member) {
        SimpleMemberInfoRes newer = new SimpleMemberInfoRes();

        newer.id = member.getId();
        newer.nickname = member.getNickname();
        newer.role = member.getRole();
        newer.profileImageUrl = member.getProfileImageUrl();

        return newer;
    }

    public void decorateFamilyMember(FamilyMember familyMember) {
        Optional.ofNullable(familyMember)
                .ifPresent((member) -> {
                    this.nickname = member.getNickname();
                    this.profileImageUrl = member.getProfileImageUrl();
                });
    }
}
