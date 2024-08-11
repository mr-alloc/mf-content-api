package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeMainFamilySettingReq;
import com.cofixer.mf.mfcontentapi.dto.res.ChangeMainFamilySettingRes;
import com.cofixer.mf.mfcontentapi.dto.res.GetUserSettingRes;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.UserSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@Tag(name = "/v1/user-setting: 유저 설정")
@RequestMapping("/v1/user-setting")
public class UserSettingController {

    private final UserSettingService service;

    @Operation(summary = "/: 유저 설정조회")
    @GetMapping
    public ResponseEntity<GetUserSettingRes> getUserSetting() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        GetUserSettingRes response = service.getUserSetting(authorizedMember);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "/main-family: 메인 패밀리 설정")
    @PutMapping
    public ResponseEntity<ChangeMainFamilySettingRes> changeMainFamilySetting(
            @RequestBody ChangeMainFamilySettingReq request
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        ChangeMainFamilySettingRes response = service.changeMainFamily(authorizedMember, request);

        return ResponseEntity.ok(response);
    }

}
