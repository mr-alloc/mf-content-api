package com.cofixer.mf.mfcontentapi.dto.res;

import java.util.List;

public record GetFamilyMembers(

        List<FamilyMemberSummary> members
) {

    public static GetFamilyMembers of(List<FamilyMemberSummary> members) {
        return new GetFamilyMembers(members);
    }
}
