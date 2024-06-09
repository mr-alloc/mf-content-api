package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_mission_state")
public class MissionState implements Serializable {

    @Serial
    private static final long serialVersionUID = 1041801005363871285L;

    @Id
    Long id;

    /* 미션 ID */
    @Column(name = "mission_id", nullable = false)
    Long missionId;

    /* 상태 */
    @Column(name = "mission_status", nullable = false)
    Integer status;

    /* 미션 시작시간 (timestamp) */
    @Column(name = "start_stamp")
    Long startStamp;

    /* 미션 종료시간 (timestamp) */
    @Column(name = "end_stamp")
    Long endStamp;

    @Transient
    transient MissionStatus currentStatus;

    @PostLoad
    public void init() {
        this.currentStatus = MissionStatus.fromCode(status);
    }

    public static MissionState forCreate(Long missionId, MissionType missionType) {
        MissionState missionState = new MissionState();
        missionState.missionId = missionId;
        missionState.status = missionType.isSchedule()
                ? MissionStatus.ALWAYS.getCode()
                : MissionStatus.CREATED.getCode();
        missionState.startStamp = 0L;
        missionState.endStamp = 0L;
        return missionState;
    }

    public void changeStatus(MissionStatus status, Long timestamp) {
        switch (status) {
            case IN_PROGRESS:
                this.startStamp = timestamp;
                break;
            case COMPLETED:
                this.endStamp = timestamp;
                break;
            default:
                this.startStamp = 0L;
                this.endStamp = 0L;
                break;
        }
        this.status = status.getCode();
    }

    public void delete() {
        this.status = MissionStatus.DELETED.getCode();
    }

    public boolean isNotDeleted() {
        return this.currentStatus != MissionStatus.DELETED;
    }

//    public Long getRemainSeconds() {
//        MissionStatus currentStatus = getCurrentStatus();
//        return switch (currentStatus) {
//            case CREATED -> this.deadline;
//            case IN_PROGRESS -> {
//                long now = TemporalUtil.getEpochSecond();
//                long endDueStamp = Math.addExact(this.deadline, mission.getStartStamp());
//                yield Math.subtractExact(endDueStamp, now);
//            }
//            case COMPLETED -> Math.subtractExact(mission.getEndStamp(), mission.getStartStamp());
//            default -> 0L;
//        };
//    }
}
