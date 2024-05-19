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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@FamilyMemberAuth(MemberRoleType.REGULAR)
@RequestMapping("/v1/family/mission")
public class FamilyMissionController {

    private final MissionService missionService;

    @FamilyMemberAuth(MemberRoleType.NONE)
    @PostMapping("/create")
    public ResponseEntity<CreateFamilyMissionRes> createFamilyMission(
            @RequestBody CreateFamilyMissionReq request
    ) {
        AuthorizedMember member = AuthorizedService.getMember();
        CreateFamilyMissionRes createdResponse = missionService.createFamilyMission(request, member);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdResponse);
    }


    @GetMapping("/calendar")
    public ResponseEntity<GetFamilyCalendarRes> getFamilyCalendar(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetFamilyCalendarRes response = missionService.getFamilyCalendar(authorizedMember, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<GetFamilyMissionDetailRes> getFamilyMissionDetail(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        FamilyMissionDetailValue missionDetail = missionService.getFamilyMissionDetail(authorizedMember, missionId);
        GetFamilyMissionDetailRes response = GetFamilyMissionDetailRes.of(missionDetail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<ChangeFamilyMissionRes> changeFamilyMission(
            @PathVariable("missionId") Long missionId,
            @RequestBody ChangeFamilyMissionReq request
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        ChangeFamilyMissionRes response = missionService.changeFamilyMission(authorizedMember, missionId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<DeleteFamilyMissionRes> deleteFamilyMission(@PathVariable("missionId") Long missionId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        DeleteFamilyMissionRes response = missionService.deleteFamilyMission(missionId, authorizedMember);
        return ResponseEntity.ok(response);
    }
}
