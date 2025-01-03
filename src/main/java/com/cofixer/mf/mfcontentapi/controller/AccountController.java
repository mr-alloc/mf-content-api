package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ConfirmAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.RefreshTokenReq;
import com.cofixer.mf.mfcontentapi.dto.req.VerifyAccountReq;
import com.cofixer.mf.mfcontentapi.dto.res.AccountInfoRes;
import com.cofixer.mf.mfcontentapi.dto.res.RefreshTokenRes;
import com.cofixer.mf.mfcontentapi.dto.res.VerifiedAccountRes;
import com.cofixer.mf.mfcontentapi.service.AccountService;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@AccountAuth(AccountRoleType.GUEST)
@RestController
@Tag(name = "/v1/account: 계정")
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "이메일 확인")
    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmAccount(@RequestBody ConfirmAccountReq req) {
        accountService.confirmAccount(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "계정 생성")
    @PostMapping("/create")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountReq req) {
        accountService.createAccount(req);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "계정 확인")
    @PostMapping("/verify")
    public ResponseEntity<VerifiedAccountRes> verifyAccount(@RequestBody VerifyAccountReq req) {
        VerifiedAccountRes verifiedRes = accountService.verifyAccount(req);
        return ResponseEntity.ok(verifiedRes);
    }

    @AccountAuth(AccountRoleType.MEMBER)
    @Operation(summary = "계정정보 조회")
    @GetMapping("/info")
    public ResponseEntity<AccountInfoRes> getAccountInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        AccountInfoRes accountInfoRes = accountService.getAccountInfo(
                authorizedMember.getAccountId()
        );

        return ResponseEntity.ok(accountInfoRes);
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenRes> refreshToken(
            @RequestBody RefreshTokenReq req
    ) {
        String accessToken = accountService.createAccessToken(req);
        return ResponseEntity.ok(RefreshTokenRes.of(accessToken));
    }
}
