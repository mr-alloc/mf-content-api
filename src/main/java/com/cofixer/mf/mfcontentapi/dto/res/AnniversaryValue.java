package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.constant.Days;
import com.cofixer.mf.mfcontentapi.domain.Anniversary;

import java.util.List;

public record AnniversaryValue(
        Long id,
        Integer type,
        String name,
        Long memberId,
        Long familyId,
        Long startAt,
        Long endAt,
        String yearMonth,
        List<Integer> days
) {
    public static AnniversaryValue of(Anniversary anniversary) {
        return new AnniversaryValue(
                anniversary.getId(),
                anniversary.getType(),
                anniversary.getName(),
                anniversary.getReporter(),
                anniversary.getFamily(),
                anniversary.getStartAt(),
                anniversary.getEndAt(),
                anniversary.getYearMonth(),
                Days.toBits(anniversary.getDays())
        );
    }
}
