package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.domain.Discussion;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.domain.MissionState;
import com.cofixer.mf.mfcontentapi.dto.res.DiscussionValue;
import com.cofixer.mf.mfcontentapi.manager.DiscussionManager;
import com.cofixer.mf.mfcontentapi.manager.MissionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class DiscussionService {

    private final DiscussionManager manager;
    private final MissionManager missionManager;

    public Discussion getDiscussionOrRetrieve(MissionState state) {
        return getDiscussionOrRetrieve(state, null);
    }

    public Discussion getDiscussionOrRetrieve(Mission mission, MissionState state) {
        return getDiscussionOrRetrieve(state, () -> mission);
    }

    private Discussion getDiscussionOrRetrieve(MissionState state, Supplier<Mission> missionSupplier) {
        return Optional.ofNullable(manager.getDiscussionByStateId(state.getId()))
                .orElseGet(() -> {
                    Mission mission = missionSupplier != null
                            ? missionSupplier.get()
                            : missionManager.getMission(state.getMissionId());
                    return manager.saveDiscussion(Discussion.of(mission.getName(), state.getId()));
                });
    }

    public List<DiscussionValue> getDiscussionValues(Set<Long> states, Map<Long, MissionState> stateMap) {
        return manager.getDiscussionAll(states).stream()
                .filter(discussion -> stateMap.containsKey(discussion.getStateId()))
                .map(discussion -> DiscussionValue.of(discussion, stateMap.get(discussion.getStateId())))
                .sorted(Comparator.comparing(DiscussionValue::latestUpdateTime, Comparator.reverseOrder()))
                .toList();
    }
}
