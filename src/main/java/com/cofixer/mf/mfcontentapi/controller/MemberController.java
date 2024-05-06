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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<SimpleMemberInfoRes> getMemberInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        SimpleMemberInfoRes response = memberService.getSimpleMemberInfo(authorizedMember);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/nickname")
    public ResponseEntity<Void> changeNickname(@RequestBody ChangeNicknameReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        memberService.changeNickname(authorizedMember, req);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<MemberDetailRes> getMemberDetail() {
        AuthorizedMember info = AuthorizedService.getMember();
        MemberDetailRes detail = memberService.getMemberDetail(info);
        return ResponseEntity.ok(detail);
    }

    @PostMapping("/request/join")
    public ResponseEntity<RequestFamilyRes> requestConnectFamily(@RequestBody FamilyJoinReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Family requestedFamily = memberService.requestFamilyMember(req, authorizedMember);

        return ResponseEntity.ok(RequestFamilyRes.of(requestedFamily));
    }


    @DeleteMapping("/cancel/{familyId}")
    public ResponseEntity<CancelFamilyRequestRes> cancelConnectRequest(@PathVariable Long familyId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long canceledFamilyId = memberService.cancelFamilyRequest(familyId, authorizedMember);

        return ResponseEntity.ok(CancelFamilyRequestRes.of(canceledFamilyId));
    }

    @GetMapping("/invite_requests")
    public ResponseEntity<GetInviteRequestRes> getInviteRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<InviteRequestRes> connectRequests = memberService.getOwnConnectRequests(authorizedMember, FamilyMemberDirection.FAMILY_TO_MEMBER);

        return ResponseEntity.ok(GetInviteRequestRes.of(connectRequests));
    }

    @GetMapping("/own_requests")
    public ResponseEntity<GetInviteRequestRes> getOwnRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<InviteRequestRes> connectRequests = memberService.getOwnConnectRequests(authorizedMember, FamilyMemberDirection.MEMBER_TO_FAMILY);

        return ResponseEntity.ok(GetInviteRequestRes.of(connectRequests));
    }

    @PostMapping("/accept/{familyId}")
    public ResponseEntity<AcceptFamilyRequestRes> acceptConnectRequest(@PathVariable Long familyId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long newFamilyId = memberService.acceptFamilyRequest(familyId, authorizedMember);

        return ResponseEntity.ok(AcceptFamilyRequestRes.of(newFamilyId));
    }

    @PostMapping("/reject/{familyId}")
    public ResponseEntity<RejectFamilyRequestRes> rejectConnectRequest(@PathVariable Long familyId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long rejectedFamilyId = memberService.rejectFamilyRequest(familyId, authorizedMember);

        return ResponseEntity.ok(RejectFamilyRequestRes.of(rejectedFamilyId));
    }
}
