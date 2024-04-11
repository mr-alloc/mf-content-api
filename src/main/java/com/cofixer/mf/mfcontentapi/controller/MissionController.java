package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.MemberAuth;
import com.cofixer.mf.mfcontentapi.domain.IndividualMission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
import com.cofixer.mf.mfcontentapi.dto.req.GetFamilyCalendarRes;
import com.cofixer.mf.mfcontentapi.dto.res.CreateMissionRes;
import com.cofixer.mf.mfcontentapi.dto.res.GetMemberCalendarRes;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@MemberAuth
@RequestMapping("/v1/mission")
public class MissionController {

    private final MissionService missionService;


    @PostMapping("/create")
    public ResponseEntity<CreateMissionRes> createMission(@RequestBody CreateMissionReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        IndividualMission mission = missionService.createMission(req, authorizedMember.getMemberId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateMissionRes(mission.getId()));
    }

    @GetMapping("/member-calendar")
    public ResponseEntity<GetMemberCalendarRes> getMemberCalendar(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetMemberCalendarRes response = missionService.getMemberCalendar(authorizedMember.getMemberId(), startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/family-calendar")
    public ResponseEntity<GetFamilyCalendarRes> getFamilyCalendar(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetFamilyCalendarRes response = missionService.getFamilyCalendar(authorizedMember, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
