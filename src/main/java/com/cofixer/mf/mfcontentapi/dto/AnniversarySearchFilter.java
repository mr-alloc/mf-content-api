package com.cofixer.mf.mfcontentapi.dto;

public record AnniversarySearchFilter(
        Long reporterId,
        Long familyId,
        boolean isFamily,
        Long startAt,
        Long endAt
) {
    public static AnniversarySearchFilter of(AuthorizedMember authorizedMember, Long startAt, Long endAt) {
        return new AnniversarySearchFilter(
                authorizedMember.getMemberId(),
                authorizedMember.getFamilyId(),
                authorizedMember.forFamilyMember(),
                startAt,
                endAt
        );
    }
}
