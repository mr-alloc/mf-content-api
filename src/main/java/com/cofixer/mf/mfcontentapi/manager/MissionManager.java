package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import com.cofixer.mf.mfcontentapi.domain.FamilyMissionDetail;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.domain.MissionDetail;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.repository.FamilyMissionDetailRepository;
import com.cofixer.mf.mfcontentapi.repository.MissionDetailRepository;
import com.cofixer.mf.mfcontentapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissionManager {

    private final MissionDetailRepository missionDetailRepository;
    private final FamilyMissionDetailRepository familyMissionDetailRepository;
    private final MissionRepository missionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public MissionDetail saveMissionDetail(Mission mission, MissionDetail missionDetail) {
        Mission newMission = missionRepository.save(mission);
        missionDetail.couplingMission(newMission);

        return missionDetailRepository.save(missionDetail);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMissionDetail saveFamilyMission(Mission mission, FamilyMissionDetail familyMission) {
        Mission newMission = missionRepository.save(mission);
        familyMission.couplingMission(newMission);

        return familyMissionDetailRepository.save(familyMission);
    }


    public List<MissionDetail> getMissions(Collection<Long> scheduleIds) {
        return missionDetailRepository.getMissionInPeriod(scheduleIds);
    }

    public List<FamilyMissionDetail> getFamilyMissions(Collection<Long> scheduleIds) {
        return familyMissionDetailRepository.getMissionsInPeriod(scheduleIds);
    }

    public MissionDetail getMissionDetail(Long missionId) {
        return missionDetailRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_INDIVIDUAL_MISSION));
    }

    public FamilyMissionDetail getFamilyMissionDetail(Long missionId, Long familyId) {
        return Optional.ofNullable(familyMissionDetailRepository.getFamilyMission(missionId, familyId))
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_FAMILY_MISSION));
    }
}
