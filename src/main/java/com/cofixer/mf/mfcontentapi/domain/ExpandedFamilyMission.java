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
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "mf_expanded_family_mission", indexes = {
        @Index(name = "idx_family_id", columnList = "family_id"),
})
public class ExpandedFamilyMission implements Serializable {
    @Serial
    private static final long serialVersionUID = -7457655598407925966L;

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "family_id")
    Long familyId;

    /* 담당자 아이디 */
    @Column(name = "assignee_id")
    Long assigneeId;

    /* 미션 종료 예정시간 */
    @Column(name = "end_due_stamp", nullable = false)
    Long endDueStamp;

    /* 마지막 업데이트 멤버 */
    @Column(name = "last_update_member", nullable = false)
    Long lastUpdateMember;

    @Delegate
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", referencedColumnName = "id")
    Mission mission;

    public static ExpandedFamilyMission forCreate(
            CreateFamilyMissionReq request,
            FamilyMember assignee,
            AuthorizedMember authorizedMember
    ) {
        ExpandedFamilyMission newer = new ExpandedFamilyMission();

        newer.familyId = authorizedMember.getFamilyId();
        newer.assigneeId = assignee.getMemberId();
        newer.endDueStamp = request.getEndDueDate();
        newer.lastUpdateMember = authorizedMember.getMemberId();

        return newer;
    }

    public void changeAssignee(Long assigneeId, Long updateMemberId, LocalDateTime now) {
        this.assigneeId = assigneeId;
        this.lastUpdateMember = updateMemberId;
        mission.renewUpdatedAt(now);
    }

    public void changeTitle(String title, Long updateMemberId, LocalDateTime now) {
        mission.changeTitle(title, now);
        this.lastUpdateMember = updateMemberId;
    }

    public void changeStatus(MissionStatus status, Long updateMemberId, LocalDateTime now) {
        this.lastUpdateMember = updateMemberId;
        mission.changeStatus(status, now);
    }

    public void delete(Long updateMemberId) {
        mission.delete();
        this.lastUpdateMember = updateMemberId;
    }

    public void changeType(MissionType missionType, Long memberId, LocalDateTime now) {
        mission.changeType(missionType, now);
        this.lastUpdateMember = memberId;
    }

    public long getRemainSeconds() {
        MissionStatus status = mission.getCurrentStatus();
        return switch (status) {
            case CREATED, IN_PROGRESS -> Math.subtractExact(mission.getEndStamp(), TemporalUtil.getEpochSecond());
            case COMPLETED -> Math.subtractExact(mission.getEndStamp(), mission.getStartStamp());
            default -> 0;
        };
    }

    public Long getEstimatedSeconds() {
        return Optional.ofNullable(this.endDueStamp)
                .map(ownEndDueStamp -> Math.subtractExact(ownEndDueStamp, mission.getStartDueStamp()))
                .orElse(0L);
    }

    public void couplingMission(Mission newMission) {
        this.id = newMission.getId();
    }
}
