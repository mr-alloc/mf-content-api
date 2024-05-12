package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.aspect.FamilyMemberAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyReq;
import com.cofixer.mf.mfcontentapi.dto.req.InviteFamilyRes;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.FamilyMemberService;
import com.cofixer.mf.mfcontentapi.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FamilyMemberAuth(MemberRoleType.REGULAR)
@AccountAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/family")
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyMemberService familyMemberService;

    @PostMapping("/create")
    public ResponseEntity<CreateFamilyRes> createFamily(@RequestBody CreateFamilyReq req) {
        AuthorizedMember info = AuthorizedService.getMember();
        Family created = familyService.createFamily(req, info.getMemberId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateFamilyRes(created.getId()));
    }

    @GetMapping("/info")
    public ResponseEntity<GetFamilyInfoRes> getFamilyInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetFamilyInfoRes familyInfo = familyService.getFamilyInfo(authorizedMember);

        return ResponseEntity.ok(familyInfo);
    }

    @PutMapping("/member/nickname")
    public ResponseEntity<Void> changeNickname(@RequestBody ChangeNicknameReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        familyMemberService.changeNickname(authorizedMember, req);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/own")
    public ResponseEntity<GetFamilyRes> getOwnFamilies() {
        AuthorizedMember member = AuthorizedService.getMember();
        List<FamilySummary> familySummaries = familyService.getFamilySummaries(member.getMemberId());

        return ResponseEntity.ok(GetFamilyRes.of(familySummaries));
    }

    @GetMapping("/members")
    public ResponseEntity<GetFamilyMembers> getFamilyMembers() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<FamilyMemberSummary> familyMembers = familyService.getFamilyMembers(authorizedMember);

        return ResponseEntity.ok(GetFamilyMembers.of(familyMembers));
    }

    @FamilyMemberAuth(MemberRoleType.MASTER)
    @PostMapping("/invite/{memberId}")
    public ResponseEntity<InviteFamilyRes> inviteFamilyMember(@PathVariable Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long invitedMemberId = familyService.inviteFamilyMember(memberId, authorizedMember);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new InviteFamilyRes(invitedMemberId));
    }

    @FamilyMemberAuth(MemberRoleType.SUB_MASTER)
    @GetMapping("/join_requests")
    public ResponseEntity<GetJoinRequestRes> getJoinRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<JoinRequestRes> connectRequests = familyService.getMemberConnectRequests(authorizedMember, FamilyMemberDirection.MEMBER_TO_FAMILY);

        return ResponseEntity.ok(GetJoinRequestRes.of(connectRequests));
    }

    @FamilyMemberAuth(MemberRoleType.MASTER)
    @DeleteMapping("/invite/{memberId}")
    public ResponseEntity<CancelMemberRequestRes> cancelConnectRequest(@PathVariable("memberId") Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long canceledMemberId = familyService.cancelFamilyMember(memberId, authorizedMember);

        return ResponseEntity.ok(CancelMemberRequestRes.of(canceledMemberId));
    }

    @FamilyMemberAuth(MemberRoleType.SUB_MASTER)
    @GetMapping("/own_requests")
    public ResponseEntity<GetJoinRequestRes> getOwnRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<JoinRequestRes> connectRequests = familyService.getMemberConnectRequests(authorizedMember, FamilyMemberDirection.FAMILY_TO_MEMBER);

        return ResponseEntity.ok(GetJoinRequestRes.of(connectRequests));
    }

    @FamilyMemberAuth(MemberRoleType.MASTER)
    @PostMapping("/accept/{memberId}")
    public ResponseEntity<AcceptMemberRequestRes> acceptConnectRequest(@PathVariable("memberId") Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long newMemberId = familyService.acceptFamilyMember(memberId, authorizedMember);

        return ResponseEntity.ok(AcceptMemberRequestRes.of(newMemberId));
    }

    @FamilyMemberAuth(MemberRoleType.MASTER)
    @PostMapping("/reject/{memberId}")
    public ResponseEntity<RejectMemberRequestRes> rejectConnectRequest(@PathVariable("memberId") Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long rejectedMemberId = familyService.rejectFamilyMember(memberId, authorizedMember);

        return ResponseEntity.ok(RejectMemberRequestRes.of(rejectedMemberId));
    }
}
