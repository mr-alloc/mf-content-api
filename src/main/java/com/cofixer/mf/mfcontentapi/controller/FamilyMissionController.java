package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.aspect.FamilyMemberAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeFamilyMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.GetFamilyCalendarRes;
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
@FamilyMemberAuth(MemberRoleType.REGULAR)
@Tag(name = "/v1/family/mission: 패밀리 미션")
@RequestMapping("/v1/family/mission")
public class FamilyMissionController {

    private final MissionService missionService;

    @Operation(summary = "패밀리 생성")
    @PostMapping
    public ResponseEntity<CreateFamilyMissionRes> createFamilyMission(
            @RequestBody CreateFamilyMissionReq request
    ) {
        AuthorizedMember member = AuthorizedService.getMember();
        List<FamilyMissionDetailValue> created = missionService.createFamilyMission(request, member);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateFamilyMissionRes.of(created));
    }


    @Operation(summary = "패밀리 캘린더 조회")
    @GetMapping("/calendar")
    public ResponseEntity<GetFamilyCalendarRes> getFamilyCalendar(
            @RequestParam("startAt") Long startAt,
            @RequestParam("endAt") Long endAt
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetFamilyCalendarRes response = missionService.getFamilyCalendar(authorizedMember, startAt, endAt);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "미션조회")
    @GetMapping("/{missionId}")
    public ResponseEntity<GetFamilyMissionDetailRes> getFamilyMissionDetail(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        FamilyMissionDetailValue missionDetail = missionService.getFamilyMissionDetail(authorizedMember, missionId);
        GetFamilyMissionDetailRes response = GetFamilyMissionDetailRes.of(missionDetail);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "미션 변경")
    @PutMapping("/{missionId}")
    public ResponseEntity<ChangeFamilyMissionRes> changeFamilyMission(
            @PathVariable("missionId") Long missionId,
            @RequestBody ChangeFamilyMissionReq request
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        ChangeFamilyMissionRes response = missionService.changeFamilyMission(authorizedMember, missionId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "미션 삭제")
    @DeleteMapping("/{missionId}")
    public ResponseEntity<DeleteFamilyMissionRes> deleteFamilyMission(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        DeleteFamilyMissionRes response = missionService.deleteFamilyMission(missionId, authorizedMember);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "일일 미션 목록 조회")
    @GetMapping("/daily")
    public ResponseEntity<GetTodayFamilyMissionsRes> getComingMissions(
            @Schema(description = "시작 기준값")
            @RequestParam("timestamp") Long timestamp
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetTodayFamilyMissionsRes response = missionService.getTodayFamilyMissions(authorizedMember, timestamp);
        return ResponseEntity.ok(response);
    }
}
