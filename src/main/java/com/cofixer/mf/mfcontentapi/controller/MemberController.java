package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.dto.req.FamilyJoinReq;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@Tag(name = "/v1/member: 멤버")
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    @Operation(summary = "/info: 멤버정보 조회")
    public ResponseEntity<SimpleMemberInfoRes> getMemberInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        SimpleMemberInfoRes response = memberService.getSimpleMemberInfo(authorizedMember);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/nickname")
    @Operation(summary = "/nickname: 닉네임 변경")
    public ResponseEntity<Void> changeNickname(@RequestBody ChangeNicknameReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        memberService.changeNickname(authorizedMember, req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내 정보 조회")
    @GetMapping("/detail")
    public ResponseEntity<MemberDetailRes> getMemberDetail() {
        AuthorizedMember info = AuthorizedService.getMember();
        MemberDetailRes detail = memberService.getMemberDetail(info);
        return ResponseEntity.ok(detail);
    }

    @Operation(summary = "패밀리 가입요청")
    @PostMapping("/join_request")
    public ResponseEntity<RequestFamilyRes> requestConnectFamily(@RequestBody FamilyJoinReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Family requestedFamily = memberService.requestFamilyMember(req, authorizedMember);

        return ResponseEntity.ok(RequestFamilyRes.of(requestedFamily));
    }

    @Operation(summary = "패밀리 가입요청 취소")
    @DeleteMapping("/join_request/{familyId}")
    public ResponseEntity<CancelFamilyRequestRes> cancelConnectRequest(@PathVariable Long familyId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long canceledFamilyId = memberService.cancelFamilyRequest(familyId, authorizedMember);

        return ResponseEntity.ok(CancelFamilyRequestRes.of(canceledFamilyId));
    }

    @Operation(summary = "패밀리 가입요청")
    @GetMapping("/invite_request")
    public ResponseEntity<GetInviteRequestRes> getInviteRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<InviteRequestRes> connectRequests = memberService.getOwnConnectRequests(authorizedMember, FamilyMemberDirection.FAMILY_TO_MEMBER);

        return ResponseEntity.ok(GetInviteRequestRes.of(connectRequests));
    }

    @Operation(summary = "패밀리 가입 요청목록 조회")
    @GetMapping("/join_request/own")
    public ResponseEntity<GetInviteRequestRes> getOwnRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<InviteRequestRes> connectRequests = memberService.getOwnConnectRequests(authorizedMember, FamilyMemberDirection.MEMBER_TO_FAMILY);

        return ResponseEntity.ok(GetInviteRequestRes.of(connectRequests));
    }

    @Operation(summary = "패밀리 초대 수락")
    @PostMapping("/accept/{familyId}")
    public ResponseEntity<AcceptFamilyRequestRes> acceptConnectRequest(@PathVariable Long familyId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long newFamilyId = memberService.acceptFamilyRequest(familyId, authorizedMember);

        return ResponseEntity.ok(AcceptFamilyRequestRes.of(newFamilyId));
    }

    @Operation(summary = "패밀리 초대 거절")
    @PostMapping("/reject/{familyId}")
    public ResponseEntity<RejectFamilyRequestRes> rejectConnectRequest(@PathVariable Long familyId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long rejectedFamilyId = memberService.rejectFamilyRequest(familyId, authorizedMember);

        return ResponseEntity.ok(RejectFamilyRequestRes.of(rejectedFamilyId));
    }
}
