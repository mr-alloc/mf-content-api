package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("미션 상태 정보")
@Table(name = "mf_mission_state")
public class MissionState implements Serializable {

    @Serial
    private static final long serialVersionUID = 1041801005363871285L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("미션 ID")
    @Column(name = "mission_id", nullable = false)
    Long missionId;

    /* 상태 */
    @Comment("미션 상태[0: 생성, 1: 진행중, 2: 완료, 3: 삭제, 4: 항상진행(일정)]")
    @Column(name = "mission_status", nullable = false)
    Integer status;

    @Comment("미션 시작시간 (timestamp)")
    @Column(name = "start_stamp")
    Long startStamp;

    @Comment("미션 종료시간 (timestamp)")
    @Column(name = "end_stamp")
    Long endStamp;

    @Comment("실제 시작시간")
    @Column(name = "concrete_start_time", nullable = false)
    Long startTime;

    @Comment("실제 완료시간")
    @Column(name = "concrete_complete_time", nullable = false)
    Long completeTime;

    @Transient
    transient MissionStatus currentStatus;

    @PostLoad
    public void init() {
        this.currentStatus = MissionStatus.fromCode(status);
    }

    public static MissionState forMissionCreate(Long missionId, MissionType missionType, Schedule schedule) {
        MissionState missionState = new MissionState();
        missionState.missionId = missionId;
        missionState.status = missionType.isSchedule()
                ? MissionStatus.ALWAYS.getCode()
                : MissionStatus.CREATED.getCode();
        missionState.startStamp = schedule.getStartAt();
        missionState.endStamp = schedule.getEndAt();
        missionState.startTime = 0L;
        missionState.completeTime = 0L;
        return missionState;
    }

    public static MissionState forLazyCreate(Long missionId, MissionType missionType, Schedule schedule, Long timestamp) {
        MissionState missionState = new MissionState();
        missionState.missionId = missionId;
        missionState.status = missionType.isSchedule()
                ? MissionStatus.ALWAYS.getCode()
                : MissionStatus.CREATED.getCode();

        missionState.startTime = 0L;
        missionState.completeTime = 0L;

        if (ScheduleMode.REPEAT.equalsValue(schedule.getMode())) {
            missionState.startStamp = timestamp;
            missionState.endStamp = timestamp + (TemporalUtil.DAY_IN_SECONDS - 1);
            return missionState;
        }

        missionState.startStamp = schedule.getStartAt();
        missionState.endStamp = schedule.getEndAt();

        return missionState;
    }

    public void changeStatus(MissionStatus status) {
        if (status == MissionStatus.IN_PROGRESS) {
            //실제 시작시간을 적용
            this.startTime = TemporalUtil.getEpochSecond();
        } else if (status == MissionStatus.COMPLETED) {
            this.completeTime = TemporalUtil.getEpochSecond();
        }
        this.status = status.getCode();
    }

    public void delete() {
        this.status = MissionStatus.DELETED.getCode();
    }

    public boolean isNotDeleted() {
        return this.currentStatus != MissionStatus.DELETED;
    }

}
