package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.constant.RegularExpression;
import com.cofixer.mf.mfcontentapi.util.ValidateUtil;

import java.util.regex.Matcher;

public record AnniversarySearchFilter(
        Long reporterId,
        Long familyId,
        Boolean isFamily,
        String yearMonth,
        Integer year,
        Integer month
) {
    public static AnniversarySearchFilter of(AuthorizedMember authorizedMember, String yearMonth) {
        Matcher matcher = ValidateUtil.assertValidGetMatcher(yearMonth, RegularExpression.YEAR_MONTH);

        return new AnniversarySearchFilter(
                authorizedMember.getMemberId(),
                authorizedMember.getFamilyId(),
                authorizedMember.forFamilyMember(),
                yearMonth,
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2))
        );
    }
}
