package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_expanded_mission")
public class ExpandedMission implements Serializable {
    @Serial
    private static final long serialVersionUID = 290358945892945000L;

    @Id
    @Column(name = "id")
    Long id;

    /* 데드라인 (초) */
    @Column(name = "deadline")
    Long deadline;

    @Delegate
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", referencedColumnName = "id")
    Mission mission;


    public static ExpandedMission forCreate(CreateMissionReq req) {
        ExpandedMission newMission = new ExpandedMission();

        newMission.deadline = req.getDeadline();

        return newMission;
    }

    public void changeDeadLine(Long deadline, LocalDateTime now) {
        this.deadline = deadline;
        mission.renewUpdatedAt(now);
    }

    public Long getRemainSeconds() {
        MissionStatus currentStatus = mission.getCurrentStatus();
        return switch (currentStatus) {
            case CREATED -> this.deadline;
            case IN_PROGRESS -> {
                long now = TemporalUtil.getEpochSecond();
                long endDueStamp = Math.addExact(this.deadline, mission.getStartStamp());
                yield Math.subtractExact(endDueStamp, now);
            }
            case COMPLETED -> Math.subtractExact(mission.getEndStamp(), mission.getStartStamp());
            default -> 0L;
        };
    }

    public void couplingMission(Mission newMission) {
        this.id = newMission.getId();
        switch (MissionType.fromValue(newMission.getMissionType())) {
            case SCHEDULE -> this.deadline = 0L;
        }
    }
}
