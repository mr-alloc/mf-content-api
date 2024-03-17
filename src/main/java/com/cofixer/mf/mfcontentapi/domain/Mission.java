package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_mission")
public class Mission implements Serializable {

    @Serial
    private static final long serialVersionUID = -1923390060168771625L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    /* 미션명 */
    @Column(name = "mission_name", nullable = false)
    String name;

    /* 생성자 아이디 */
    @Column(name = "reporter_id", nullable = false)
    Long reporterId;

    /* 담당자 아이디 */
    @Column(name = "assignee_id")
    Long assigneeId;

    /* 미션 타입 1: 일반미션, 2: 미션팩, 3: 스텝미션 */
    @Column(name = "mission_type", nullable = false)
    Integer missionType;

    /* 데드라인 타임스탬프 */
    @Column(name = "dead_line")
    Long deadLine;

    /* 생성일 */
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    public static Mission forCreate(CreateMissionReq req, Long memberId) {
        Mission newMission = new Mission();

        newMission.name = req.getMissionName();
        newMission.reporterId = memberId;
        newMission.assigneeId = Optional.ofNullable(req.getAssignee()).orElse(memberId);
        newMission.missionType = req.getMissionType();

        LocalDateTime today = LocalDateTime.now(AppContext.APP_CLOCK);
        newMission.createdAt = today.toEpochSecond(AppContext.APP_ZONE_OFFSET);
        newMission.deadLine = today.plusSeconds(req.getDeadline()).toEpochSecond(AppContext.APP_ZONE_OFFSET);

        return newMission;
    }
}
