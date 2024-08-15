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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@AccountAuth(AccountRoleType.MEMBER)
@RequestMapping("/v1/state")
public class MissionStateController {

    private final MissionStateService missionStateService;


    @PostMapping("/{stateId}/comment")
    public ResponseEntity<CreateCommentRes> createComment(
            @PathVariable("stateId") Long stateId,
            @RequestBody CreateCommentReq req
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        MissionCommentValue created = missionStateService.createComment(authorizedMember, stateId, req);
        return ResponseEntity.ok(CreateCommentRes.of(created));
    }

    @GetMapping("/{stateId}/comment")
    public ResponseEntity<GetCommentsRes> getComments(
            @PathVariable("stateId") Long stateId
    ) {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<MissionCommentValue> comments = missionStateService.getComments(authorizedMember, stateId);
        return ResponseEntity.ok(GetCommentsRes.of(comments));
    }

    @GetMapping("/discussions")
    public ResponseEntity<GetDiscussionsRes> getDiscussions() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();
        List<DiscussionValue> joinedDiscussions = missionStateService.getJoinedDiscussions(authorizedMember);
        return ResponseEntity.ok(GetDiscussionsRes.of(joinedDiscussions));
    }
}
