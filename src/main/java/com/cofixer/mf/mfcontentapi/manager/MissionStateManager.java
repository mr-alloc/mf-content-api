package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.MissionState;
import com.cofixer.mf.mfcontentapi.repository.MissionStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MissionStateManager {

    private final MissionStateRepository repository;

    public List<MissionState> getStates(Long missionId) {
        return repository.getStateAllByMissionId(missionId);
    }

    public MissionState getState(Long missionId, Long startStamp) {
        return repository.getState(missionId, startStamp);
    }

    public List<MissionState> getStateAll(Collection<Long> missionIdList) {
        return repository.getStateAllByMissionIdList(missionIdList);
    }

    public Optional<MissionState> getState(Long stateId) {
        return repository.findById(stateId);
    }
}

