package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.FamilyMission;
import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.repository.FamilyMissionRepository;
import com.cofixer.mf.mfcontentapi.repository.IndividualMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionManager {

    private final IndividualMissionRepository individualMissionRepository;
    private final FamilyMissionRepository familyMissionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public IndividualMission saveIndividualMission(IndividualMission mission) {
        return individualMissionRepository.save(mission);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMission saveFamilyMission(FamilyMission mission) {
        return familyMissionRepository.save(mission);
    }


    public List<IndividualMission> getMissions(Long mid, long startTime, long endTime) {
        return individualMissionRepository.findPeriodMissions(mid, startTime, endTime);
    }

    public List<FamilyMission> getFamilyMissions(AuthorizedMember authorizedMember, long startTime, long endTime) {
        return familyMissionRepository.findPeriodMissions(authorizedMember, startTime, endTime);
    }
}
