package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
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
import java.util.Optional;

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
    Boolean isPublic;

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

    /**/
    @Column(name = "watcher", nullable = false)
    String watchers;

    /* 생성일 */
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    /* 수정일 */
    @Column(name = "updated_at", nullable = false)
    Long updatedAt;

    /* 미션 시작일 */
    @Column(name = "start_date")
    Long startDate;

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

    protected Mission(String name, Long reporter, Long assignee, Integer missionType, Long startDate, LocalDateTime now) {
        this.status = MissionStatus.CREATED.getCode();
        this.isPublic = true;
        this.name = name;
        this.reporterId = reporter;
        this.assigneeId = Optional.ofNullable(assignee).orElse(reporter);
        this.missionType = missionType;
        this.watchers = "";
        this.startDate = startDate;
        this.createdAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
        this.updatedAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }

    public abstract Long getId();

    public void changeStatus(MissionStatus status) {
        this.status = status.getCode();
    }

    public void renewUpdatedAt(LocalDateTime now) {
        this.updatedAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }
}
