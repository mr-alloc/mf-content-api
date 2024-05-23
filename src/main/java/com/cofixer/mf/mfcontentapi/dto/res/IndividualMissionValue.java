package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;
public record IndividualMissionValue(

        /* 미션 ID */
        Long id,

        /* 미션 타입 */
        Integer type,

        /* 미션 상태 */
        Integer status,

        /* 미션명 */
        String name,

        /* 기한*/
        Long deadLine,

        Long startDate
) {

    public static IndividualMissionValue of(ExpandedMission mission) {
        return new IndividualMissionValue(
                mission.getId(),
                mission.getMissionType(),
                mission.getStatus(),
                mission.getName(),
                mission.getDeadline(),
                mission.getStartDueStamp()
        );
    }
}
