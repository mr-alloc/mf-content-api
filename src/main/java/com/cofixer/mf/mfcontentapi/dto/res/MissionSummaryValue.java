package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
public record MissionSummaryValue(

        /* 미션 ID */
        Long missionId,

        /* 미션명 */
        String missionName,

        /* 기한*/
        Long deadLine
) {

    public static MissionSummaryValue of(IndividualMission mission) {
        return new MissionSummaryValue(mission.getId(), mission.getName(), mission.getDeadLine());
    }
}
