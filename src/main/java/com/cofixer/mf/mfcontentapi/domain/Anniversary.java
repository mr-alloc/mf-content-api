package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.Days;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAnniversaryReq;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_anniversary", indexes = {
        @Index(name = "idx_family", columnList = "family"),
        @Index(name = "idx_reporter", columnList = "reporter"),
        @Index(name = "idx_between_period", columnList = "start_at, end_at"),
        @Index(name = "idx_year_month_days", columnList = "yearMonth, days")
})
public class Anniversary implements Serializable {

    @Serial
    private static final long serialVersionUID = -8105954004534441527L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Integer type;


    @Column(nullable = false)
    String name;


    @Column(nullable = false)
    Long reporter;

    Long family;


    @Column(name = "start_at")
    Long startAt;

    @Column(name = "end_at")
    Long endAt;

    // KST 기준
    String yearMonth;

    // KST 기준
    Integer days;

    @Column(name = "created_at", nullable = false)
    Long createdAt;

    public static Anniversary forPeriod(AuthorizedMember authorizedMember, CreateAnniversaryReq req) {
        Anniversary anniversary = new Anniversary();
        anniversary.type = req.type();
        anniversary.name = req.name();
        anniversary.reporter = authorizedMember.getMemberId();
        anniversary.family = authorizedMember.forFamilyMember() ? authorizedMember.getFamilyId() : 0L;
        anniversary.startAt = req.startAt();
        anniversary.endAt = req.endAt();
        anniversary.days = 0;
        anniversary.createdAt = TemporalUtil.getEpochSecond();
        return anniversary;
    }

    public static Anniversary forMultiple(AuthorizedMember authorizedMember, CreateAnniversaryReq req) {
        Anniversary anniversary = new Anniversary();
        anniversary.type = req.type();
        anniversary.name = req.name();
        anniversary.reporter = authorizedMember.getMemberId();
        anniversary.family = authorizedMember.forFamilyMember() ? authorizedMember.getFamilyId() : 0L;
        anniversary.startAt = 0L;
        anniversary.endAt = 0L;
        anniversary.yearMonth = req.yearMonth();
        anniversary.days = Days.toSelected(req.days());
        anniversary.createdAt = TemporalUtil.getEpochSecond();
        return anniversary;
    }
}
