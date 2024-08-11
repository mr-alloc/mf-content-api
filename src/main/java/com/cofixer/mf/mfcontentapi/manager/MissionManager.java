package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMissionResult;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMissionDetail;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.domain.MissionDetail;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.exception.MissionException;
import com.cofixer.mf.mfcontentapi.repository.FamilyMissionDetailRepository;
import com.cofixer.mf.mfcontentapi.repository.MissionDetailRepository;
import com.cofixer.mf.mfcontentapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionManager {

    private final MissionDetailRepository missionDetailRepository;
    private final FamilyMissionDetailRepository familyMissionDetailRepository;
    private final MissionRepository missionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public MissionDetail saveMissionDetail(Mission mission) {
        Mission newMission = missionRepository.save(mission);

        return missionDetailRepository.save(MissionDetail.forCreate(newMission));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMissionDetail saveFamilyMissionDetail(Mission mission, FamilyMember assignee, AuthorizedMember authorizedMember) {
        Mission newMission = missionRepository.save(mission);
        FamilyMissionDetail familyMission = FamilyMissionDetail.forCreate(newMission, assignee, authorizedMember);

        return familyMissionDetailRepository.save(familyMission);
    }


    public List<MissionDetail> getMissionDetails(Collection<Long> scheduleIds) {
        return missionDetailRepository.getMissionsInPeriod(scheduleIds);
    }

    public List<FamilyMissionDetail> getFamilyMissionDetails(Collection<Long> scheduleIds) {
        return familyMissionDetailRepository.getMissionsInPeriod(scheduleIds);
    }

    public Map<Long, FamilyMissionDetail> getFamilyMissionDetailsMap(Collection<Long> scheduleIds) {
        return familyMissionDetailRepository.getMissionsInPeriod(scheduleIds).stream()
                .collect(Collectors.toMap(
                        FamilyMissionDetail::getMissionId,
                        Function.identity()
                ));
    }

    public MissionDetail getMissionDetail(Long missionId) {
        return missionDetailRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_INDIVIDUAL_MISSION));
    }

    public FamilyMissionDetail getFamilyMissionDetail(Long missionId, Long familyId) {
        return Optional.ofNullable(familyMissionDetailRepository.getFamilyMission(missionId, familyId))
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_FAMILY_MISSION));
    }

    public Mission getMission(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(DeclaredMissionResult.NOT_FOUND_MISSION));
    }

    public Map<Long, Mission> getMissionsMap(Set<Long> missionIds) {
        return missionRepository.findAllById(missionIds).stream()
                .collect(Collectors.toMap(
                        Mission::getMissionId,
                        Function.identity()
                ));
    }

    public List<Mission> getMissions(Set<Long> missionIds) {
        return missionRepository.findByMissionIdIn(missionIds);
    }

    public Map<Long, MissionDetail> getMissionDetailsMap(Set<Long> scheduleIds) {
        return getMissionDetails(scheduleIds).stream()
                .collect(Collectors.toMap(
                        MissionDetail::getMissionId,
                        Function.identity()
                ));
    }
}
