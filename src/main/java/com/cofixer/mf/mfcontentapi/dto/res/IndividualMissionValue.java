package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
public record IndividualMissionValue(

        /* 미션 ID */
        Long id,

        /* 미션명 */
        String name,

        /* 기한*/
        Long deadLine,

        Long startDate
) {

    public static IndividualMissionValue of(IndividualMission mission) {
        return new IndividualMissionValue(
                mission.getId(),
                mission.getName(),
                mission.getDeadLine(),
                mission.getStartDate()
        );
    }
}
