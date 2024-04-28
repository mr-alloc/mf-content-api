package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyReq;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@AccountAuth
@RequestMapping("/v1/family")
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/create")
    public ResponseEntity<CreateFamilyRes> createFamily(@RequestBody CreateFamilyReq req) {
        AuthorizedMember info = AuthorizedService.getMember();
        Family created = familyService.createFamily(req, info.getMemberId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateFamilyRes(created.getId()));
    }

    @GetMapping("/own")
    public ResponseEntity<GetFamilyRes> getOwnFamilies() {
        AuthorizedMember member = AuthorizedService.getMember();
        List<FamilySummary> familySummaries = familyService.getFamilySummaries(member.getMemberId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetFamilyRes.of(familySummaries));
    }

    @GetMapping("/members")
    public ResponseEntity<GetFamilyMembers> getFamilyMembers() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<FamilyMemberSummary> familyMembers = familyService.getFamilyMembers(authorizedMember);

        return ResponseEntity.ok(GetFamilyMembers.of(familyMembers));
    }
}
