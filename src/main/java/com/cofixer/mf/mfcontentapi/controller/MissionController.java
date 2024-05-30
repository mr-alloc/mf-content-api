package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/mission")
public class MissionController {

    private final MissionService missionService;


    @PostMapping
    public ResponseEntity<CreateMissionRes> createMission(@RequestBody CreateMissionReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        CreateMissionRes createdResponse = missionService.createMission(req, authorizedMember);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdResponse);
    }

    @GetMapping("/calendar")
    public ResponseEntity<GetMemberCalendarRes> getMemberCalendar(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetMemberCalendarRes response = missionService.getMemberCalendar(authorizedMember.getMemberId(), startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<GetMissionDetailRes> getMissionDetail(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        MissionDetailValue missionDetail = missionService.getMissionDetail(authorizedMember.getMemberId(), missionId);
        GetMissionDetailRes response = GetMissionDetailRes.of(missionDetail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<ChangeMissionRes> changeMission(
            @PathVariable("missionId") Long missionId,
            @RequestBody ChangeMissionReq req
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        ChangeMissionRes response = missionService.changeMission(missionId, req, authorizedMember);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<DeleteMissionRes> deleteMission(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        DeleteMissionRes response = missionService.deleteMission(missionId, authorizedMember);
        return ResponseEntity.ok(response);
    }
}
