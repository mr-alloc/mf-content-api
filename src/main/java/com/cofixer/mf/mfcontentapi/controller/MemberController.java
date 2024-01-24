package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
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
public class MemberController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountReq req) {
        Account account = accountService.createAccount(req);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
