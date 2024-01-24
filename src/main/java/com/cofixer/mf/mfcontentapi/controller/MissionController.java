package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.dto.res.CreateMissionRes;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/mission")
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/create")
    public ResponseEntity<CreateMissionRes> createMission(@RequestBody CreateMissionReq req) {
        Mission mission = missionService.createMission(req);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateMissionRes(mission.getId()));
    }
}
