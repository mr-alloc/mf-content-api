package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.MissionCommentValue;
import com.cofixer.mf.mfcontentapi.dto.req.CreateCommentReq;
import com.cofixer.mf.mfcontentapi.dto.res.CreateCommentRes;
import com.cofixer.mf.mfcontentapi.dto.res.DiscussionValue;
import com.cofixer.mf.mfcontentapi.dto.res.GetCommentsRes;
import com.cofixer.mf.mfcontentapi.dto.res.GetDiscussionsRes;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.MissionStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@Tag(name = "/v1/state", description = "미션 상태")
@RequestMapping("/v1/state")
public class MissionStateController {

    private final MissionStateService missionStateService;


    @Operation(summary = "의견 추가")
    @PostMapping("/{stateId}/comment")
    public ResponseEntity<CreateCommentRes> createComment(
            @PathVariable("stateId") Long stateId,
            @RequestBody CreateCommentReq req
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        CreateCommentRes response = missionStateService.createComment(authorizedMember, stateId, req);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "의견 목록 조회")
    @GetMapping("/{stateId}/comment")
    public ResponseEntity<GetCommentsRes> getComments(
            @PathVariable("stateId") Long stateId
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<MissionCommentValue> comments = missionStateService.getComments(authorizedMember, stateId);
        return ResponseEntity.ok(GetCommentsRes.of(comments));
    }

    @Operation(summary = "토론 목록")
    @GetMapping("/discussions")
    public ResponseEntity<GetDiscussionsRes> getDiscussions() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<DiscussionValue> joinedDiscussions = missionStateService.getJoinedDiscussions(authorizedMember);
        return ResponseEntity.ok(GetDiscussionsRes.of(joinedDiscussions));
    }
}
