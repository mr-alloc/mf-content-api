package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.MemberAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedInfo;
import com.cofixer.mf.mfcontentapi.dto.res.SimpleMemberInfo;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @MemberAuth(AccountRoleType.MEMBER)
    @GetMapping("/info")
    public ResponseEntity<SimpleMemberInfo> getMemberInfo() {
        AuthorizedInfo info = AuthorizedService.getInfo();

        SimpleMemberInfo response = memberService.getSimpleMemberInfo(info.mid());
        return ResponseEntity.ok(response);
    }
}
