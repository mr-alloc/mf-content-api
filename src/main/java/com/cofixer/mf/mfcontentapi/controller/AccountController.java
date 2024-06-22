package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ConfirmAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.VerifyAccountReq;
import com.cofixer.mf.mfcontentapi.dto.res.AccountInfoRes;
import com.cofixer.mf.mfcontentapi.dto.res.VerifiedAccountRes;
import com.cofixer.mf.mfcontentapi.service.AccountService;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@AccountAuth(AccountRoleType.GUEST)
@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmAccount(@RequestBody ConfirmAccountReq req) {
        accountService.confirmAccount(req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountReq req) {
        accountService.createAccount(req);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifiedAccountRes> verifyAccount(@RequestBody VerifyAccountReq req) {
        VerifiedAccountRes verifiedRes = accountService.verifyAccount(req);
        return ResponseEntity.ok(verifiedRes);
    }

    @GetMapping("/info")
    @AccountAuth(AccountRoleType.MEMBER)
    public ResponseEntity<AccountInfoRes> getAccountInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        AccountInfoRes accountInfoRes = accountService.getAccountInfo(
                authorizedMember.getAccountId()
        );

        return ResponseEntity.ok(accountInfoRes);
    }
}
