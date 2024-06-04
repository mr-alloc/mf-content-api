package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.RepeatOption;
import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_schedule", indexes = {
        @Index(name = "idx_family", columnList = "family"),
        @Index(name = "idx_reporter", columnList = "reporter"),
        @Index(name = "idx_between_period", columnList = "start_at, end_at")
})
public class Schedule implements Serializable {

    @Serial
    private static final long serialVersionUID = 4551833486103457038L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type")
    Integer type;

    @Column(name = "reporter", nullable = false)
    Long reporter;

    @Column(name = "family")
    Long family;

    @Column(name = "mode", nullable = false)
    Integer mode;

    @Column(name = "start_at")
    Long startAt;

    @Column(name = "end_at")
    Long endAt;

    @Column(name = "repeat_option", nullable = false)
    Integer repeatOption;

    @Column(name = "repeat_value", nullable = false)
    Long repeatValue;

    public static List<Schedule> of(AuthorizedMember authorizedMember, ScheduleInfo scheduleInfo, ScheduleType scheduleType) {

        ScheduleMode scheduleMode = ScheduleMode.fromValue(scheduleInfo.scheduleMode());

        return switch (scheduleMode) {
            case PERIOD -> {
                Schedule schedule = new Schedule();
                schedule.type = scheduleType.getValue();
                schedule.reporter = authorizedMember.getMemberId();
                schedule.family = authorizedMember.getFamilyId();
                schedule.mode = scheduleMode.getValue();
                schedule.startAt = scheduleInfo.startAt();
                schedule.endAt = scheduleInfo.endAt();
                schedule.repeatOption = RepeatOption.NONE.getValue();
                schedule.repeatValue = 0L;
                yield List.of(schedule);
            }
            case SINGLE, MULTIPLE -> scheduleInfo.selected().stream().map(timestamp -> {
                Schedule schedule = new Schedule();
                schedule.type = scheduleType.getValue();
                schedule.reporter = authorizedMember.getMemberId();
                schedule.family = authorizedMember.getFamilyId();
                schedule.mode = scheduleMode.getValue();
                schedule.startAt = scheduleInfo.startAt();
                schedule.endAt = Math.addExact(timestamp, TemporalUtil.DAY_IN_SECONDS);
                schedule.repeatOption = RepeatOption.NONE.getValue();
                schedule.repeatValue = 0L;
                return schedule;
            }).toList();
            case REPEAT -> {
                Schedule schedule = new Schedule();
                schedule.type = scheduleType.getValue();
                schedule.reporter = authorizedMember.getMemberId();
                schedule.family = authorizedMember.getFamilyId();
                schedule.mode = scheduleMode.getValue();
                schedule.startAt = scheduleInfo.startAt();
                schedule.endAt = scheduleInfo.endAt();
                schedule.repeatOption = scheduleInfo.repeatOption();
                schedule.repeatValue = scheduleInfo.repeatValue();
                yield List.of(schedule);
            }
        };
    }
}
