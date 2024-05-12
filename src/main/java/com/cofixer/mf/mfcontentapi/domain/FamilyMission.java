package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyMissionReq;
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

    @Column(name = "start_stamp")
    Long startStamp;

    @Column(name = "end_stamp")
    Long endStamp;

    public FamilyMission(String name, Long reporter, Long assignee, Integer missionType, Long startDate, LocalDateTime now) {
        super(name, reporter, assignee, missionType, startDate, now);
    }

    public static FamilyMission forCreate(
            CreateFamilyMissionReq request,
            FamilyMember assignee,
            AuthorizedMember authorizedMember
    ) {
        LocalDateTime now = LocalDateTime.now(AppContext.APP_CLOCK);
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

        return newMission;
    }
}
