package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionStatus;
import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyMissionReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
@Table(name = "mf_family_mission_detail")
public class FamilyMissionDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = -7457655598407925966L;

    @Id
    @Column(name = "mission_id")
    Long missionId;

    /* 담당자 아이디 */
    @Column(name = "assignee_id")
    Long assigneeId;

    /* 마지막 업데이트 멤버 */
    @Column(name = "last_update_member", nullable = false)
    Long lastUpdateMember;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mission_id", insertable = false, updatable = false)
    Mission mission;

    public static FamilyMissionDetail forCreate(
            CreateFamilyMissionReq request,
            FamilyMember assignee,
            AuthorizedMember authorizedMember
    ) {
        FamilyMissionDetail newer = new FamilyMissionDetail();

        newer.assigneeId = assignee.getMemberId();
        newer.lastUpdateMember = authorizedMember.getMemberId();

        return newer;
    }

    public void changeAssignee(Long assigneeId, Long updateMemberId, Long now) {
        this.assigneeId = assigneeId;
        this.lastUpdateMember = updateMemberId;
        mission.renewUpdatedAt(now);
    }

    public void changeTitle(String title, Long updateMemberId, Long now) {
        mission.changeTitle(title, now);
        this.lastUpdateMember = updateMemberId;
    }

    public void changeStatus(MissionStatus status, Long updateMemberId, Long now) {
        this.lastUpdateMember = updateMemberId;
        mission.changeStatus(status, now);
    }

    public void delete(Long updateMemberId) {
        mission.delete();
        this.lastUpdateMember = updateMemberId;
    }

    public void changeType(MissionType missionType, Long memberId, Long now) {
        mission.changeType(missionType, now);
        this.lastUpdateMember = memberId;
    }

    public void couplingMission(Mission newMission) {
        this.missionId = newMission.getId();
    }
}
