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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FamilyMemberAuth(MemberRoleType.REGULAR)
@AccountAuth(AccountRoleType.MEMBER)
@Tag(name = "/v1/family", description = "패밀리")
@RequestMapping("/v1/family")
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyMemberService familyMemberService;

    @Operation(summary = "생성")
    @PostMapping("/create")
    public ResponseEntity<CreateFamilyRes> createFamily(@RequestBody CreateFamilyReq req) {
        AuthorizedMember info = AuthorizedService.getMember();
        Family created = familyService.createFamily(req, info.getMemberId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateFamilyRes(created.getId()));
    }

    @Operation(summary = "정보조회")
    @GetMapping("/info")
    public ResponseEntity<GetFamilyInfoRes> getFamilyInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        GetFamilyInfoRes familyInfo = familyService.getFamilyInfo(authorizedMember);

        return ResponseEntity.ok(familyInfo);
    }

    @Operation(summary = "닉네임 변경")
    @PutMapping("/member/nickname")
    public ResponseEntity<Void> changeNickname(@RequestBody ChangeNicknameReq req) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        familyMemberService.changeNickname(authorizedMember, req);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "패밀리 조회")
    @GetMapping("/own")
    public ResponseEntity<GetOwnFamiliesRes> getOwnFamilies() {
        AuthorizedMember member = AuthorizedService.getMember();
        List<FamilySummary> familySummaries = familyService.getFamilySummaries(member.getMemberId());

        return ResponseEntity.ok(GetOwnFamiliesRes.of(familySummaries));
    }

    @Operation(summary = "멤버 목록조회")
    @GetMapping("/members")
    public ResponseEntity<GetFamilyMembers> getFamilyMembers() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<FamilyMemberSummary> familyMembers = familyService.getFamilyMembers(authorizedMember);

        return ResponseEntity.ok(GetFamilyMembers.of(familyMembers));
    }

    @Operation(summary = "초대 요청")
    @FamilyMemberAuth(MemberRoleType.MASTER)
    @PostMapping("/invite/{memberId}")
    public ResponseEntity<InviteFamilyRes> inviteFamilyMember(@PathVariable Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long invitedMemberId = familyService.inviteFamilyMember(memberId, authorizedMember);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new InviteFamilyRes(invitedMemberId));
    }

    @Operation(summary = "가입 요청")
    @FamilyMemberAuth(MemberRoleType.SUB_MASTER)
    @GetMapping("/join_requests")
    public ResponseEntity<GetJoinRequestRes> getJoinRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<JoinRequestRes> connectRequests = familyService.getMemberConnectRequests(authorizedMember, FamilyMemberDirection.MEMBER_TO_FAMILY);

        return ResponseEntity.ok(GetJoinRequestRes.of(connectRequests));
    }

    @Operation(summary = "초대 취소")
    @FamilyMemberAuth(MemberRoleType.MASTER)
    @DeleteMapping("/invite/{memberId}")
    public ResponseEntity<CancelMemberRequestRes> cancelConnectRequest(@PathVariable("memberId") Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long canceledMemberId = familyService.cancelFamilyMember(memberId, authorizedMember);

        return ResponseEntity.ok(CancelMemberRequestRes.of(canceledMemberId));
    }

    @Operation(summary = "초대 목록")
    @FamilyMemberAuth(MemberRoleType.SUB_MASTER)
    @GetMapping("/own_requests")
    public ResponseEntity<GetJoinRequestRes> getOwnRequests() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<JoinRequestRes> connectRequests = familyService.getMemberConnectRequests(authorizedMember, FamilyMemberDirection.FAMILY_TO_MEMBER);

        return ResponseEntity.ok(GetJoinRequestRes.of(connectRequests));
    }

    @Operation(summary = "가입요청 수락")
    @FamilyMemberAuth(MemberRoleType.MASTER)
    @PostMapping("/accept/{memberId}")
    public ResponseEntity<AcceptMemberRequestRes> acceptConnectRequest(@PathVariable("memberId") Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long newMemberId = familyService.acceptFamilyMember(memberId, authorizedMember);

        return ResponseEntity.ok(AcceptMemberRequestRes.of(newMemberId));
    }

    @Operation(summary = "가입요청 거절")
    @FamilyMemberAuth(MemberRoleType.MASTER)
    @PostMapping("/reject/{memberId}")
    public ResponseEntity<RejectMemberRequestRes> rejectConnectRequest(@PathVariable("memberId") Long memberId) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        Long rejectedMemberId = familyService.rejectFamilyMember(memberId, authorizedMember);

        return ResponseEntity.ok(RejectMemberRequestRes.of(rejectedMemberId));
    }

    @Operation(summary = "나의 멤버정보")
    @GetMapping("/members/own")
    public ResponseEntity<GetFamilyMemberInfo> getFamilyMemberInfo() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        FamilyMemberInfo familyMemberInfo = familyService.getFamilyMemberInfo(authorizedMember);

        return ResponseEntity.ok(GetFamilyMemberInfo.of(familyMemberInfo));
    }
}
