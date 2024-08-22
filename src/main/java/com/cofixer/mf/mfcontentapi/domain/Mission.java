package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.util.StringUtil;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("미션 메인 정보")
@DynamicUpdate
@Table(name = "mf_mission", indexes = {
        @Index(name = "idx_schedule_id", columnList = "schedule_id"),
        @Index(name = "idx_place_id", columnList = "place_id"),
})
public class Mission implements Serializable {

    @Serial
    private static final long serialVersionUID = -1923390060168771625L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Comment("공개 여부")
    @Column(name = "is_public", nullable = false)
    boolean isPublic;

    @Comment("미션명")
    @Column(name = "mission_name", nullable = false)
    String name;

    @Comment("미션 설명")
    @Column(name = "description", length = 500)
    String description;

    @Comment("스케쥴 ID")
    @Column(name = "schedule_id", nullable = false)
    Long scheduleId;

    @Comment("장소 ID")
    @Column(name = "place_id")
    Long placeId;

    @Comment("미션 타입 1: 일반미션, 2: 미션팩, 3: 스텝미션")
    @Column(name = "mission_type", nullable = false)
    Integer missionType;

    @Comment("관람자, 스케쥴일 경우 참가자")
    @Column(name = "watcher", nullable = false)
    String watchers;

    @Comment("제한시간(초): 시작 시 적용")
    @Column(name = "deadline", nullable = false)
    Long deadline;

    @Comment("수정일시")
    @Column(name = "updated_at", nullable = false)
    Long updatedAt;

    @Comment("생성일시")
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    @Transient
    transient List<Long> watcherList = new ArrayList<>();

    @PostLoad
    public void init() {
        if (StringUtil.isNotEmpty(watchers)) {
            this.watcherList = Arrays.stream(watchers.split(","))
                    .map(Long::parseLong)
                    .toList();
        }
    }

    public static Mission forCreate(CreateMissionReq req, Schedule schedule, Long timeStamp) {
        Mission newer = new Mission();
        newer.isPublic = true;
        newer.name = req.name();
        newer.description = req.subName();
        newer.scheduleId = schedule.getId();
        newer.placeId = 0L;
        newer.missionType = req.type();
        newer.watchers = String.valueOf(schedule.getReporter());
        newer.deadline = req.deadline()
                .filter(deadline -> !MissionType.fromValue(newer.missionType).isSchedule())
                .orElse(0L);
        newer.createdAt = timeStamp;
        newer.updatedAt = timeStamp;

        return newer;
    }

    public static Mission forCreate(CreateFamilyMissionReq req, Schedule schedule, Long timestamp) {
        Mission newer = new Mission();
        newer.isPublic = true;
        newer.name = req.name();
        newer.description = req.subName();
        newer.scheduleId = schedule.getId();
        newer.placeId = 0L;
        newer.missionType = req.type();
        newer.watchers = String.valueOf(schedule.getReporter());
        newer.deadline = req.deadline()
                .filter(deadline -> !MissionType.fromValue(newer.missionType).isSchedule())
                .orElse(0L);
        newer.createdAt = timestamp;
        newer.updatedAt = timestamp;

        return newer;
    }


    public void changeTitle(String title, Long timestamp) {
        this.name = title;
        renewUpdatedAt(timestamp);
    }

    public void changeDeadLine(Long deadline, Long now) {
        this.deadline = deadline;
        renewUpdatedAt(now);
    }

    public void renewUpdatedAt(Long timestamp) {
        this.updatedAt = timestamp;
    }


    public void delete() {
        this.renewUpdatedAt(TemporalUtil.getEpochSecond());
    }

    public void changeType(MissionType missionType, Long now) {
        this.missionType = missionType.getValue();
        this.renewUpdatedAt(now);
    }

    public void changeDescription(String description, long now) {
        this.description = description;
        this.renewUpdatedAt(now);
    }

}

