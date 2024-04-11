package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "mf_individual_mission", indexes = {
        @Index(name = "idx_reporter_id", columnList = "reporter_id")
})
public class IndividualMission extends Mission implements Serializable {
    @Serial
    private static final long serialVersionUID = 290358945892945000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    /* 미션 부제 */
    @Column(name = "sub_name")
    String subName;

    /* 데드라인 타임스탬프 */
    @Column(name = "dead_line")
    Long deadLine;

    public IndividualMission(CreateMissionReq req, Long memberId, LocalDateTime now) {
        super(req.getMissionName(), memberId, req.getAssignee(), req.getMissionType(), now);
    }

    public static IndividualMission forCreate(CreateMissionReq req, Long memberId) {
        LocalDateTime now = LocalDateTime.now(AppContext.APP_CLOCK);
        IndividualMission newMission = new IndividualMission(req, memberId, now);

        newMission.subName = req.getMissionSubName();
        newMission.deadLine = now.plusSeconds(req.getDeadline()).toEpochSecond(AppContext.APP_ZONE_OFFSET);

        return newMission;
    }
}
