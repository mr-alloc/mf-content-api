package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
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

    public FamilyMission(CreateMissionReq req, Long memberId, LocalDateTime now) {
        super(req.getMissionName(), memberId, req.getAssignee(), req.getMissionType(), now);
    }

    public static FamilyMission forCreate(CreateMissionReq req, AuthorizedMember authorizedMember) {
        LocalDateTime now = LocalDateTime.now(AppContext.APP_CLOCK);
        FamilyMission newMission = new FamilyMission(req, authorizedMember.getMemberId(), now);

        newMission.familyId = authorizedMember.getFamilyId();

        return newMission;
    }
}
