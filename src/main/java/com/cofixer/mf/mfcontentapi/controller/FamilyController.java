package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.MemberAuth;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyReq;
import com.cofixer.mf.mfcontentapi.dto.res.CreateFamilyRes;
import com.cofixer.mf.mfcontentapi.dto.res.FamilySummary;
import com.cofixer.mf.mfcontentapi.dto.res.GetFamilyRes;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@MemberAuth
@RequestMapping("/v1/family")
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/create")
    public ResponseEntity<CreateFamilyRes> createFamily(@RequestBody CreateFamilyReq req) {
        AuthorizedMember info = AuthorizedService.getMember();
        Family created = familyService.createFamily(req, info.mid());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateFamilyRes(created.getId()));
    }

    @GetMapping
    public ResponseEntity<GetFamilyRes> getFamilies() {
        AuthorizedMember member = AuthorizedService.getMember();
        List<FamilySummary> familySummaries = familyService.getFamilySummaries(member.mid());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetFamilyRes.of(familySummaries));
    }
}
