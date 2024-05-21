package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import com.cofixer.mf.mfcontentapi.domain.ExpandedFamilyMission;
import com.cofixer.mf.mfcontentapi.domain.ExpandedMission;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.repository.ExpandedFamilyMissionRepository;
import com.cofixer.mf.mfcontentapi.repository.ExpandedMissionRepository;
import com.cofixer.mf.mfcontentapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionManager {

    private final ExpandedMissionRepository expandedMissionRepository;
    private final ExpandedFamilyMissionRepository expandedFamilyMissionRepository;
    private final MissionRepository missionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public ExpandedMission saveExpandedMission(Mission mission, ExpandedMission expandedMission) {
        Mission newMission = missionRepository.save(mission);
        expandedMission.couplingMission(newMission);

        return expandedMissionRepository.save(expandedMission);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ExpandedFamilyMission saveFamilyMission(Mission mission, ExpandedFamilyMission familyMission) {
        Mission newMission = missionRepository.save(mission);
        familyMission.couplingMission(newMission);

        return expandedFamilyMissionRepository.save(familyMission);
    }


    public List<ExpandedMission> getMissions(Long mid, long startTime, long endTime) {
        return expandedMissionRepository.findPeriodMissions(mid, startTime, endTime);
    }

    public List<ExpandedFamilyMission> getFamilyMissions(AuthorizedMember authorizedMember, long startTime, long endTime) {
        return expandedFamilyMissionRepository.findPeriodMissions(authorizedMember, startTime, endTime);
    }

    public ExpandedMission getIndividualMission(Long missionId) {
        return expandedMissionRepository.findById(missionId)
                .filter(ExpandedMission::isNotDeleted)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_INDIVIDUAL_MISSION));
    }

    public ExpandedFamilyMission getFamilyMission(Long missionId, Long familyId) {
        return expandedFamilyMissionRepository.findByIdAndFamilyId(missionId, familyId)
                .filter(ExpandedFamilyMission::isNotDeleted)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_FAMILY_MISSION));
    }
}
