package com.cofixer.mf.mfcontentapi.dto.res;

public record GetFamilyMemberInfo(

        FamilyMemberInfo memberInfo
) {

    public static GetFamilyMemberInfo of(FamilyMemberInfo familyMemberInfo) {
        return new GetFamilyMemberInfo(
                familyMemberInfo
        );
    }
}
