package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.RepeatOption;
import com.cofixer.mf.mfcontentapi.constant.ScheduleMode;
import com.cofixer.mf.mfcontentapi.constant.ScheduleType;
import com.cofixer.mf.mfcontentapi.constant.Weeks;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.ScheduleInfo;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("스케쥴 정보")
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
    @Column(name = "id")
    Long scheduleId;

    @Comment("스케쥴 타입 1: 기념일, 2: 미션")
    @Column(name = "type")
    Integer type;

    @Comment("생성멤버")
    @Column(name = "reporter", nullable = false)
    Long reporter;

    @Comment("패밀리 ID")
    @Column(name = "family")
    Long family;

    @Comment("스케쥴 모드[1: 단일, 2: 다중, 3: 기간, 4: 반복]")
    @Column(name = "mode", nullable = false)
    Integer mode;

    @Comment("시작일")
    @Column(name = "start_at")
    Long startAt;

    @Comment("스케쥴 시간")
    @Column(name = "schedule_time")
    Long scheduleTime;

    @Comment("종료일")
    @Column(name = "end_at")
    Long endAt;

    @Comment("반복 옵션[-1: 없음, 0: 매주, 1: 매월, 2: 매년]")
    @Column(name = "repeat_option", nullable = false)
    Integer repeatOption;

    @Comment("반복 값[repeat_option 0: 각 요일별 Bit 위치 합, 2 또는 3: 기준일]")
    @Column(name = "repeat_value", nullable = false)
    Integer repeatValue;

    public static List<Schedule> forCreate(AuthorizedMember authorizedMember, ScheduleInfo scheduleInfo, ScheduleType scheduleType) {
        ScheduleMode scheduleMode = ScheduleMode.fromValue(scheduleInfo.scheduleMode());

        return switch (scheduleMode) {
            case PERIOD -> {
                Schedule schedule = new Schedule();
                schedule.type = scheduleType.getValue();
                schedule.reporter = authorizedMember.getMemberId();
                schedule.family = authorizedMember.getFamilyId();
                schedule.mode = scheduleMode.getValue();
                schedule.startAt = scheduleInfo.startAt();
                schedule.scheduleTime = scheduleInfo.scheduleTime();
                schedule.endAt = scheduleInfo.endAt();
                schedule.repeatOption = RepeatOption.NONE.getValue();
                schedule.repeatValue = 0;
                yield List.of(schedule);
            }
            case SINGLE, MULTIPLE -> scheduleInfo.selected().stream().map(timestamp -> {
                Schedule schedule = new Schedule();
                schedule.type = scheduleType.getValue();
                schedule.reporter = authorizedMember.getMemberId();
                schedule.family = authorizedMember.getFamilyId();
                schedule.mode = scheduleMode.getValue();
                schedule.startAt = timestamp;
                schedule.scheduleTime = scheduleInfo.scheduleTime();
                schedule.endAt = timestamp + TemporalUtil.DAY_IN_SECONDS - 1;
                schedule.repeatOption = RepeatOption.NONE.getValue();
                schedule.repeatValue = 0;
                return schedule;
            }).toList();
            case REPEAT -> {
                Schedule schedule = new Schedule();
                schedule.type = scheduleType.getValue();
                schedule.reporter = authorizedMember.getMemberId();
                schedule.family = authorizedMember.getFamilyId();
                schedule.mode = scheduleMode.getValue();
                schedule.startAt = scheduleInfo.startAt();
                schedule.scheduleTime = scheduleInfo.scheduleTime();
                schedule.endAt = scheduleInfo.endAt();
                schedule.repeatOption = scheduleInfo.repeatOption();
                schedule.repeatValue = RepeatOption.fromValue(scheduleInfo.repeatOption()).isWeek()
                        ? Weeks.toSelected(scheduleInfo.repeatValues())
                        : scheduleInfo.getFirstRepeatValue();
                yield List.of(schedule);
            }
        };
    }

    public void editStartAt(long timestamp) {
        this.startAt = timestamp;
    }


    public List<Integer> getRepeatValues() {
        if (RepeatOption.WEEKLY.equalsValue(this.repeatOption)) {
            return Weeks.toBits(this.repeatValue);
        }

        return List.of(this.repeatValue);
    }

    public boolean isNotAccessibleFrom(AuthorizedMember authorizedMember) {
        return authorizedMember.forFamilyMember()
                ? !this.family.equals(authorizedMember.getFamilyId())
                : !this.reporter.equals(authorizedMember.getMemberId());
    }

    public void isNotAccessibleFrom(AuthorizedMember authorizedMember, Supplier<MissionException> exceptionSupplier) {
        if (isNotAccessibleFrom(authorizedMember)) {
            throw exceptionSupplier.get();
        }
    }
}
