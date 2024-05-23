package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;

public record FamilyMissionValue(
        /* 미션 ID */
        Long id,
        /* 미션 타입 */
        Integer type,
        /* 미션 상태 */
        Integer status,
        /* 미션명 */
        String name,
        /* 시작일 */
        Long startStamp,
        /* 종료일 */
        Long endStamp,
        /* 시작 예정 시간 */
        Long startDate
) {

    public static FamilyMissionValue of(ExpandedFamilyMission mission) {
        return new FamilyMissionValue(
                mission.getId(),
                mission.getMissionType(),
                mission.getStatus(),
                mission.getName(),
                mission.getStartStamp(),
                mission.getEndStamp(),
                mission.getStartDueStamp()
        );
    }
}
