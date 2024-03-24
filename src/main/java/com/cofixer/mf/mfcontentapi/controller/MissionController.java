package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.MemberAuth;
import com.cofixer.mf.mfcontentapi.domain.Mission;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateMissionReq;
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
        AuthorizedMember info = AuthorizedService.getMember();
        Mission mission = missionService.createMission(req, info.mid());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateMissionRes(mission.getId()));
    }

    @GetMapping("/member-calendar")
    public ResponseEntity<GetMemberCalendarRes> getMemberCalendar(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        AuthorizedMember info = AuthorizedService.getMember();
        GetMemberCalendarRes response = missionService.getMemberCalendar(info.mid(), startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
