package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@Tag(name = "/v1/mission: 미션")
@RequestMapping("/v1/mission")
public class MissionController {

    private final MissionService missionService;


    @Operation(summary = "/: 생성")
    @PostMapping
    public ResponseEntity<CreateMissionRes> createMission(@RequestBody CreateMissionReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<MissionDetailValue> missions = missionService.createMission(req, authorizedMember);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateMissionRes.of(missions));
    }

    @Operation(summary = "/calendar: 이번달 스케쥴 조회")
    @GetMapping("/calendar")
    public ResponseEntity<GetMemberCalendarRes> getMemberCalendar(
            @RequestParam("startAt") Long startAt,
            @RequestParam("endAt") Long endAt
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetMemberCalendarRes response = missionService.getMemberCalendar(authorizedMember, startAt, endAt);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "/{missionId}: 상세 조회")
    @GetMapping("/{missionId}")
    public ResponseEntity<GetMissionDetailRes> getMissionDetail(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        MissionDetailValue missionDetail = missionService.getMissionDetail(authorizedMember, missionId);
        GetMissionDetailRes response = GetMissionDetailRes.of(missionDetail);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "/{missionId}: 변경")
    @PutMapping("/{missionId}")
    public ResponseEntity<ChangeMissionRes> changeMission(
            @PathVariable("missionId") Long missionId,
            @RequestBody ChangeMissionReq req
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        ChangeMissionRes response = missionService.changeMission(missionId, req, authorizedMember);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "/{missionId}: 삭제")
    @DeleteMapping("/{missionId}")
    public ResponseEntity<DeleteMissionRes> deleteMission(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        DeleteMissionRes response = missionService.deleteMission(missionId, authorizedMember);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "/daily: 일일 미션 목록 조회")
    @GetMapping("/daily")
    public ResponseEntity<GetTodayMissionsRes> getComingMissions(
            @Schema(description = "시간 기준값")
            Long timestamp
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetTodayMissionsRes response = missionService.getTodayMissions(authorizedMember, timestamp);
        return ResponseEntity.ok(response);
    }
}
