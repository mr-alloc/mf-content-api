package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAnniversaryReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAnniversaryRes;
import com.cofixer.mf.mfcontentapi.dto.res.AnniversaryValue;
import com.cofixer.mf.mfcontentapi.dto.res.GetAnniversaryRes;
import com.cofixer.mf.mfcontentapi.service.AnniversaryService;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/anniversary")
public class AnniversaryController {

    private final AnniversaryService anniversaryService;

    @GetMapping
    public ResponseEntity<GetAnniversaryRes> getAnniversaries(
            @RequestParam("startAt") Long startAt,
            @RequestParam("endAt") Long endAt
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<AnniversaryValue> anniversaries = anniversaryService.getAnniversaries(startAt, endAt, authorizedMember);

        return ResponseEntity.ok(GetAnniversaryRes.of(anniversaries));
    }

    @PostMapping
    public ResponseEntity<CreateAnniversaryRes> createAnniversary(
            @RequestBody CreateAnniversaryReq req
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<AnniversaryValue> anniversaries = anniversaryService.createAnniversaries(req, authorizedMember);

        return ResponseEntity.ok(CreateAnniversaryRes.of(anniversaries));
    }
}
