package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionManager {

    private final MissionRepository repository;

    @Transactional
    public Mission saveMission(Mission mission) {
        return repository.save(mission);
    }

    public List<Mission> getMissions(Long mid, long startTime, long endTime) {
        return repository.findByAssigneeIdAndDeadLineBetween(mid, startTime, endTime);
    }
}
