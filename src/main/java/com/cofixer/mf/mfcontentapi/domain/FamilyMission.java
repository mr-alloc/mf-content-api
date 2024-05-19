package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyMissionReq;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "mf_family_mission", indexes = {
        @Index(name = "idx_family_id", columnList = "family_id"),
        @Index(name = "idx_reporter_id", columnList = "reporter_id")
})
public class FamilyMission extends Mission implements Serializable {
    @Serial
    private static final long serialVersionUID = -7457655598407925966L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "family_id")
    Long familyId;

    /* 담당자 아이디 */
    @Column(name = "assignee_id")
    Long assigneeId;

    @Column(name = "last_update_member", nullable = false)
    Long lastUpdateMember;

    public FamilyMission(String name, Long reporter, Long assignee, Integer missionType, Long startDate, LocalDateTime now) {
        super(name, reporter, missionType, startDate, now);
        this.assigneeId = assignee;
    }

    public static FamilyMission forCreate(
            CreateFamilyMissionReq request,
            FamilyMember assignee,
            AuthorizedMember authorizedMember
    ) {
        LocalDateTime now = TemporalUtil.getNow();
        FamilyMission newMission = new FamilyMission(
                request.getName(),
                authorizedMember.getMemberId(),
                assignee.getMemberId(),
                request.getType(),
                request.getStartDate(),
                now
        );
        newMission.familyId = authorizedMember.getFamilyId();
        //미션 수행자가 설정
        newMission.startStamp = 0L;
        newMission.endStamp = 0L;
        newMission.lastUpdateMember = authorizedMember.getMemberId();

        return newMission;
    }

    public void changeAssignee(Long assigneeId, Long updateMemberId, LocalDateTime now) {
        this.assigneeId = assigneeId;
        this.lastUpdateMember = updateMemberId;
        super.renewUpdatedAt(now);
    }

    public void changeTitle(String title, Long updateMemberId, LocalDateTime now) {
        super.name = title;
        this.lastUpdateMember = updateMemberId;
        super.renewUpdatedAt(now);
    }

    public void changeStatus(MissionStatus status, Long updateMemberId, LocalDateTime now) {
        super.changeStatus(status, now);
        this.lastUpdateMember = updateMemberId;
        super.renewUpdatedAt(now);
    }

    public void delete(Long updateMemberId) {
        super.delete();
        this.lastUpdateMember = updateMemberId;
    }

    public void changeType(MissionType missionType, Long memberId, LocalDateTime now) {
        super.changeType(missionType, now);
        this.lastUpdateMember = memberId;
    }

    @Override
    public long getRemainSeconds() {
        MissionStatus status = getCurrentStatus();
        return switch (status) {
            case CREATED, IN_PROGRESS -> Math.subtractExact(this.endStamp, TemporalUtil.getEpochSecond());
            default -> 0;
        };
    }
}
