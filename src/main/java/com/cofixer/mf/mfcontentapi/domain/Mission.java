package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.util.StringUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
@NoArgsConstructor
@DiscriminatorColumn
public abstract class Mission implements Serializable {

    @Serial
    private static final long serialVersionUID = -1923390060168771625L;

    /* 상태 */
    @Column(name = "mission_status", nullable = false)
    Integer status;

    /* 공개 여부 */
    @Column(name = "is_public", nullable = false)
    boolean isPublic;

    /* 미션명 */
    @Column(name = "mission_name", nullable = false)
    String name;

    /* 생성자 아이디 */
    @Column(name = "reporter_id", nullable = false)
    Long reporterId;

    /* 미션 타입 1: 일반미션, 2: 미션팩, 3: 스텝미션 */
    @Column(name = "mission_type", nullable = false)
    Integer missionType;

    /**/
    @Column(name = "watcher", nullable = false)
    String watchers;

    /* 생성일 */
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    /* 수정일 */
    @Column(name = "updated_at", nullable = false)
    Long updatedAt;

    /* 미션 시작 예정 시간 */
    @Column(name = "start_due_stamp")
    Long startDueStamp;

    /* 미션 시작시간 (timestamp) */
    @Column(name = "start_stamp")
    Long startStamp;

    /* 미션 종료시간 (timestamp) */
    @Column(name = "end_stamp")
    Long endStamp;

    @Transient
    transient List<Long> watcherList = new ArrayList<>();

    @Transient
    transient MissionStatus currentStatus;

    @PostLoad
    public void init() {
        if (StringUtil.isNotEmpty(watchers)) {
            this.watcherList = Arrays.stream(watchers.split(","))
                    .map(Long::parseLong)
                    .toList();
        }
        this.currentStatus = MissionStatus.fromCode(status);
    }

    protected Mission(String name, Long reporter, Integer missionType, Long startDate, LocalDateTime now) {
        this.status = MissionStatus.CREATED.getCode();
        this.isPublic = true;
        this.name = name;
        this.reporterId = reporter;
        this.missionType = missionType;
        this.watchers = "";
        this.startDueStamp = startDate;

        this.createdAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
        this.updatedAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }

    public abstract Long getId();

    public void changeStatus(MissionStatus status, LocalDateTime now) {
        switch (status) {
            case IN_PROGRESS:
                long timestamp = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
                //시작 에정일을 지금로 변경하여 적용
                this.startDueStamp = timestamp;
                this.startStamp = timestamp;
                break;
            case COMPLETED:
                this.endStamp = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
                break;
            default:
                this.startStamp = null;
                this.endStamp = null;
                break;
        }
        this.status = status.getCode();
        this.renewUpdatedAt(now);
    }

    public void renewUpdatedAt(LocalDateTime now) {
        this.updatedAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }

    public boolean isNotDeleted() {
        return this.currentStatus != MissionStatus.DELETED;
    }

    public void delete() {
        this.status = MissionStatus.DELETED.getCode();
        this.renewUpdatedAt(LocalDateTime.now(AppContext.APP_CLOCK));
    }

    public void changeType(MissionType missionType, LocalDateTime now) {
        this.missionType = missionType.getValue();
        this.renewUpdatedAt(now);
    }

    public abstract long getRemainSeconds();
}
