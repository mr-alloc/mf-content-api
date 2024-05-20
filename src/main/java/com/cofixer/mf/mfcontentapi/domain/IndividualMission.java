package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
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

    /* 데드라인 (초) */
    @Column(name = "deadline")
    Long deadline;

    public IndividualMission(CreateMissionReq req, Long memberId, LocalDateTime now) {
        super(req.getName(), memberId, req.getType(), req.getStartDate(), now);
    }

    public static IndividualMission forCreate(CreateMissionReq req, Long memberId) {
        LocalDateTime now = TemporalUtil.getNow();
        IndividualMission newMission = new IndividualMission(req, memberId, now);

        newMission.subName = req.getSubName();
        newMission.deadline = req.getDeadline();


        return newMission;
    }

    public void changeTitle(String title, LocalDateTime now) {
        this.name = title;
        super.renewUpdatedAt(now);
    }

    public void changeDeadLine(Long deadline, LocalDateTime now) {
        this.deadline = deadline;
        super.renewUpdatedAt(now);
    }

    @Override
    public long getRemainSeconds() {
        MissionStatus currentStatus = getCurrentStatus();
        return switch (currentStatus) {
            case CREATED -> this.deadline;
            case IN_PROGRESS -> {
                long now = TemporalUtil.getEpochSecond();
                long endDueStamp = Math.addExact(this.deadline, super.startStamp);
                yield Math.subtractExact(endDueStamp, now);
            }
            case COMPLETED -> Math.subtractExact(this.endStamp, super.startStamp);
            default -> 0;
        };
    }
}
