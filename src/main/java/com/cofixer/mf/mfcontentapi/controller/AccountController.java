package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.VerifyAccountReq;
import com.cofixer.mf.mfcontentapi.dto.res.VerifiedAccountRes;
import com.cofixer.mf.mfcontentapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountReq req) {
        Account account = accountService.createAccount(req);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifiedAccountRes> verifyAccount(@RequestBody VerifyAccountReq req) {
        VerifiedAccountRes verifiedRes = accountService.verifyAccount(req);

        return ResponseEntity.ok(verifiedRes);
    }
}
