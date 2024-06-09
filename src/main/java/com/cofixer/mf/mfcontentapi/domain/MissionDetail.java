package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_mission_detail")
public class MissionDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 290358945892945000L;

    @Id
    @Column(name = "mission_id")
    Long missionId;

    /* 데드라인 (초) */
    @Column(name = "deadline")
    Long deadline;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mission_id", insertable = false, updatable = false)
    Mission mission;


    public static MissionDetail forCreate(CreateMissionReq req) {
        MissionDetail newMission = new MissionDetail();

        req.deadline().ifPresent(deadline -> newMission.deadline = deadline);

        return newMission;
    }

    public void changeDeadLine(Long deadline, Long now) {
        this.deadline = deadline;
        mission.renewUpdatedAt(now);
    }

    public void couplingMission(Mission newMission) {
        this.missionId = newMission.getId();
        if (MissionType.fromValue(newMission.getMissionType()) == MissionType.SCHEDULE) {
            this.deadline = 0L;
        }
    }

    public Long getScheduleId() {
        return mission.getScheduleId();
    }
}
