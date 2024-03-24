package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.MemberAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.dto.res.MemberDetailRes;
import com.cofixer.mf.mfcontentapi.dto.res.SimpleMemberInfoRes;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@MemberAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<SimpleMemberInfoRes> getMemberInfo() {
        AuthorizedMember info = AuthorizedService.getMember();

        SimpleMemberInfoRes response = memberService.getSimpleMemberInfo(info.mid());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/nickname")
    public ResponseEntity<Void> changeNickname(@RequestBody ChangeNicknameReq req) {
        AuthorizedMember info = AuthorizedService.getMember();
        memberService.changeNickname(info.mid(), req);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<MemberDetailRes> getMemberDetail() {
        AuthorizedMember info = AuthorizedService.getMember();
        MemberDetailRes detail = memberService.getMemberDetail(info);
        return ResponseEntity.ok(detail);
    }
}
