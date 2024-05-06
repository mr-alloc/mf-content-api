package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.domain.*;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.dto.req.FamilyJoinReq;
import com.cofixer.mf.mfcontentapi.dto.res.InviteRequestRes;
import com.cofixer.mf.mfcontentapi.dto.res.MemberDetailRes;
import com.cofixer.mf.mfcontentapi.dto.res.SimpleMemberInfoRes;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.AccountManager;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.util.ConditionUtil;
import com.cofixer.mf.mfcontentapi.util.IterateUtil;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final AccountManager accountManager;
    private final MemberManager manager;
    private final CommonValidator commonValidator;
    private final EntityPrinter printer;
    private final ExpandableEntitySetting es;
    private final FamilyManager familyManager;

    @Transactional(readOnly = true)
    public SimpleMemberInfoRes getSimpleMemberInfo(AuthorizedMember authorizedMember) {
        Member member = manager.getMember(authorizedMember.getMemberId());
        SimpleMemberInfoRes memberInfo = SimpleMemberInfoRes.of(member);

        log.info(printer.drawEntity(member, es.getConfig()));
        if (authorizedMember.forFamilyMember()) {
            FamilyMember familyMember = familyManager.getFamilyMember(FamilyMemberId.of(authorizedMember));
            memberInfo.decorateFamilyMember(familyMember);
        }

        return memberInfo;
    }

    @Transactional
    public void changeNickname(AuthorizedMember authorizedMember, ChangeNicknameReq req) {
        commonValidator.validateNickname(req.getNickname());
        Member member = manager.getMember(authorizedMember.getMemberId());

        if (member.hasNickName() && member.nicknameIs(req.getNickname())) {
            throw new MemberException(DeclaredMemberResult.ALREADY_INITIALIZED);
        }

        member.changeNickname(req.getNickname());
    }

    @Transactional(readOnly = true)
    public MemberDetailRes getMemberDetail(AuthorizedMember authorizedMember) {
        Account found = accountManager.getExistedAccount(authorizedMember.getAccountId());

        if (authorizedMember.forFamilyMember()) {
            FamilyMember familyMember = familyManager.getFamilyMember(FamilyMemberId.of(authorizedMember));
            return MemberDetailRes.of(found, familyMember);
        }
        Member member = manager.getMember(authorizedMember.getMemberId());

        return MemberDetailRes.of(found, member);
    }


    @Transactional(readOnly = true)
    public List<InviteRequestRes> getOwnConnectRequests(AuthorizedMember authorizedMember, FamilyMemberDirection direction) {
        FamilyMemberId familyMemberId = FamilyMemberId.of(null, authorizedMember.getMemberId());
        List<FamilyMemberConnectRequest> connectRequests = familyManager.getConnectRequests(familyMemberId, direction);

        List<Long> familyIds = IterateUtil.convertList(connectRequests, FamilyMemberConnectRequest::getFamilyId);
        Map<Long, Family> familyMap = IterateUtil.toMap(familyManager.getFamilies(familyIds), Family::getId);

        return connectRequests.stream()
                .map(connectRequest -> InviteRequestRes.of(connectRequest, familyMap.get(connectRequest.getFamilyId())))
                .toList();
    }

    @Transactional
    public Long acceptFamilyRequest(Long familyId, AuthorizedMember authorizedMember) {
        Family family = familyManager.getFamily(familyId);
        FamilyMemberId familyMemberId = FamilyMemberId.of(family.getId(), authorizedMember.getMemberId());

        FamilyMemberConnectRequest requested = familyManager.getConnectRequest(familyMemberId, FamilyMemberDirection.FAMILY_TO_MEMBER);
        ConditionUtil.throwIfTrue(requested == null, () -> new MemberException(DeclaredMemberResult.NOT_FOUND_CONNECT_REQUEST));

        FamilyMember familyMember = familyManager.acceptRequestAndGetNewMember(requested);

        return familyMember.getFamilyId();
    }

    @Transactional
    public Long cancelFamilyRequest(Long familyId, AuthorizedMember authorizedMember) {
        FamilyMemberId familyMemberId = FamilyMemberId.of(familyId, authorizedMember.getMemberId());
        FamilyMemberConnectRequest connectRequest = familyManager.getConnectRequest(familyMemberId, FamilyMemberDirection.MEMBER_TO_FAMILY);
        ConditionUtil.throwIfTrue(connectRequest == null, () -> new MemberException(DeclaredMemberResult.NOT_FOUND_CONNECT_REQUEST));

        familyManager.cancelRequest(connectRequest);
        return familyId;
    }

    @Transactional
    public Long rejectFamilyRequest(Long familyId, AuthorizedMember authorizedMember) {
        FamilyMemberId familyMemberId = FamilyMemberId.of(familyId, authorizedMember.getMemberId());
        FamilyMemberConnectRequest connectRequest = familyManager.getConnectRequest(familyMemberId, FamilyMemberDirection.FAMILY_TO_MEMBER);
        ConditionUtil.throwIfTrue(connectRequest == null, () -> new MemberException(DeclaredMemberResult.NOT_FOUND_CONNECT_REQUEST));

        familyManager.cancelRequest(connectRequest);
        return familyId;
    }

    @Transactional
    public Family requestFamilyMember(FamilyJoinReq req, AuthorizedMember authorizedMember) {
        Family family = familyManager.getFamily(req.getInviteCode());
        FamilyMemberId familyMemberId = FamilyMemberId.of(family.getId(), authorizedMember.getMemberId());

        if (familyManager.isOurFamily(familyMemberId)) {
            throw new MemberException(DeclaredMemberResult.ALREADY_AFFILIATED_FAMILY);
        }

        familyManager.requestToConnect(familyMemberId, FamilyMemberDirection.MEMBER_TO_FAMILY);
        return family;
    }
}
