package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.dto.req.CreateAnniversaryReq;
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
@Comment("기념일 정보")
@Table(name = "mf_anniversary", indexes = {
        @Index(name = "idx_schedule_id", columnList = "schedule_id"),
})
public class Anniversary implements Serializable {

    @Serial
    private static final long serialVersionUID = -8105954004534441527L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("기념일명")
    @Column(nullable = false)
    String name;

    @Comment("스케쥴 ID")
    @Column(name = "schedule_id", nullable = false)
    Long scheduleId;

    @Comment("생성일시")
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    public static Anniversary forCreate(CreateAnniversaryReq req, Schedule schedule) {
        Anniversary anniversary = new Anniversary();
        anniversary.name = req.name();
        anniversary.scheduleId = schedule.getId();
        anniversary.createdAt = TemporalUtil.getEpochSecond();
        return anniversary;
    }
}
