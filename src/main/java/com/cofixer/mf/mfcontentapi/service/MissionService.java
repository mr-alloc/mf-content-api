package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional
    public Mission createMission(CreateMissionReq req) {
        Mission mission = Mission.forCreate(req);

        return missionRepository.save(mission);
    }
}
