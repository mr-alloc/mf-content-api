package com.cofixer.mf.mfcontentapi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("미션 상세 정보")
@DynamicUpdate
@Table(name = "mf_mission_detail")
public class MissionDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 290358945892945000L;

    @Id
    @Column(name = "mission_id")
    Long missionId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mission_id", insertable = false, updatable = false)
    Mission mission;


    public static MissionDetail forCreate(Mission newMission) {
        MissionDetail newMissionDetail = new MissionDetail();
        newMissionDetail.missionId = newMission.getMissionId();
        return newMissionDetail;
    }

    public Long getScheduleId() {
        return mission.getScheduleId();
    }

}
