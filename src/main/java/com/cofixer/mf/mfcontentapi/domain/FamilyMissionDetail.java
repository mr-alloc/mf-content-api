package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.MissionType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
@Comment("패밀리 미션 상세 정보")
@Table(name = "mf_family_mission_detail")
public class FamilyMissionDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = -7457655598407925966L;

    @Id
    @Comment("미션 ID")
    @Column(name = "mission_id")
    Long missionId;

    @Comment("담당자 아이디")
    @Column(name = "assignee_id")
    Long assigneeId;

    @Comment("마지막 업데이트 멤버")
    @Column(name = "last_update_member", nullable = false)
    Long lastUpdateMember;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mission_id", insertable = false, updatable = false)
    Mission mission;

    public static FamilyMissionDetail forCreate(
            Mission mission,
            FamilyMember assignee,
            AuthorizedMember authorizedMember
    ) {
        FamilyMissionDetail newer = new FamilyMissionDetail();
        newer.missionId = mission.getMissionId();
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

    public void renewLastUpdateMember(Long updateMemberId) {
        this.lastUpdateMember = updateMemberId;
    }

    public void delete(Long updateMemberId) {
        mission.delete();
        this.lastUpdateMember = updateMemberId;
    }

    public void changeType(MissionType missionType, Long memberId, Long now) {
        mission.changeType(missionType, now);
        this.lastUpdateMember = memberId;
    }

    public boolean isMyMission(AuthorizedMember authorizedMember) {
        return this.assigneeId.equals(authorizedMember.getMemberId());
    }
}
