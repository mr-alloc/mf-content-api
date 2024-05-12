package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@NoArgsConstructor
@DiscriminatorColumn
public abstract class Mission implements Serializable {

    @Serial
    private static final long serialVersionUID = -1923390060168771625L;


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

    /* 생성일 */
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    /* 미션 시작일 */
    @Column(name = "start_date")
    Long startDate;

    protected Mission(String name, Long reporter, Long assignee, Integer missionType, Long startDate, LocalDateTime now) {
        this.name = name;
        this.reporterId = reporter;
        this.assigneeId = Optional.ofNullable(assignee).orElse(reporter);
        this.missionType = missionType;
        this.startDate = startDate;
        this.createdAt = now.toEpochSecond(AppContext.APP_ZONE_OFFSET);
    }

    public abstract Long getId();
}
