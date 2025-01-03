package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredFamilyResult;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.domain.*;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyReq;
import com.cofixer.mf.mfcontentapi.dto.res.*;
import com.cofixer.mf.mfcontentapi.exception.FamilyException;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import com.cofixer.mf.mfcontentapi.util.ConditionUtil;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyService {

    private final CommonValidator commonValidator;
    private final FamilyManager familyManager;
    private final MemberManager memberManager;

    @Transactional
    public Family createFamily(CreateFamilyReq req, Long mid) {
        Family newer = Family.forCreate(req, mid);
        commonValidator.validateFamily(newer);

        Family saved = familyManager.saveFamily(newer);
        saved.initInviteCode();
        FamilyMemberId familyMemberId = FamilyMemberId.of(saved.getId(), mid);
        familyManager.registerFamilyMember(familyMemberId, MemberRoleType.MASTER);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<FamilySummary> getFamilySummaries(Long mid) {
        return familyManager.getOwnFamilies(mid);
    }

    @Transactional(readOnly = true)
    public List<FamilyMemberSummary> getFamilyMembers(AuthorizedMember authorizedMember) {
        long now = AppContext.APP_CLOCK.instant().getEpochSecond();
        return familyManager.getAllMembers(authorizedMember.getFamilyId()).stream()
                .map(familyMember -> FamilyMemberSummary.of(familyMember, now))
                .toList();
    }

    @Transactional
    public Long inviteFamilyMember(Long memberId, AuthorizedMember authorizedMember) {
        ConditionUtil.throwIfTrue(!memberManager.existMember(memberId),
                () -> new FamilyException(DeclaredFamilyResult.NOT_FOUND_MEMBER));
        FamilyMemberId familyMemberId = FamilyMemberId.of(authorizedMember.getFamilyId(), memberId);

        if (familyManager.isOurFamily(familyMemberId)) {
            throw new FamilyException(DeclaredFamilyResult.ALREADY_OUR_FAMILY_MEMBER);
        }

        FamilyMemberConnectRequest connectRequest = familyManager.requestToConnect(familyMemberId, "NO-INTRODUCE", FamilyMemberDirection.FAMILY_TO_MEMBER);

        return connectRequest.getMemberId();
    }

    @Transactional(readOnly = true)
    public List<JoinRequestRes> getMemberConnectRequests(AuthorizedMember authorizedMember, FamilyMemberDirection direction) {
        FamilyMemberId familyMemberId = FamilyMemberId.of(authorizedMember.getFamilyId(), null);
        List<FamilyMemberConnectRequest> connectRequests = familyManager.getConnectRequests(familyMemberId, direction);

        List<Long> memberIds = CollectionUtil.convertList(connectRequests, FamilyMemberConnectRequest::getMemberId);
        Map<Long, Member> memberMap = CollectionUtil.toMap(memberManager.getMembers(memberIds), Member::getId);

        return connectRequests.stream()
                .map(request -> JoinRequestRes.of(request, memberMap.get(request.getMemberId())))
                .toList();
    }

    @Transactional
    public Long acceptFamilyMember(Long memberId, AuthorizedMember authorizedMember) {
        Family family = familyManager.getFamily(authorizedMember.getFamilyId());
        FamilyMemberId familyMemberId = FamilyMemberId.of(family.getId(), memberId);

        FamilyMemberConnectRequest requested = familyManager.getConnectRequest(familyMemberId, FamilyMemberDirection.MEMBER_TO_FAMILY);
        ConditionUtil.throwIfTrue(requested == null, () -> new FamilyException(DeclaredFamilyResult.NOT_FOUND_CONNECT_REQUEST));

        FamilyMember familyMember = familyManager.acceptRequestAndGetNewMember(requested);
        return familyMember.getMemberId();
    }

    @Transactional
    public Long cancelFamilyMember(Long memberId, AuthorizedMember authorizedMember) {
        FamilyMemberId familyMemberId = FamilyMemberId.of(authorizedMember.getFamilyId(), memberId);
        FamilyMemberConnectRequest connectRequest = familyManager.getConnectRequest(familyMemberId, FamilyMemberDirection.FAMILY_TO_MEMBER);
        ConditionUtil.throwIfTrue(connectRequest == null, () -> new MemberException(DeclaredMemberResult.NOT_FOUND_CONNECT_REQUEST));

        familyManager.cancelRequest(connectRequest);
        return memberId;
    }

    @Transactional
    public Long rejectFamilyMember(Long memberId, AuthorizedMember authorizedMember) {
        FamilyMemberId familyMemberId = FamilyMemberId.of(authorizedMember.getFamilyId(), memberId);
        FamilyMemberConnectRequest connectRequest = familyManager.getConnectRequest(familyMemberId, FamilyMemberDirection.MEMBER_TO_FAMILY);
        ConditionUtil.throwIfTrue(connectRequest == null, () -> new MemberException(DeclaredMemberResult.NOT_FOUND_CONNECT_REQUEST));

        familyManager.cancelRequest(connectRequest);
        return memberId;
    }

    @Transactional(readOnly = true)
    public GetFamilyInfoRes getFamilyInfo(AuthorizedMember authorizedMember) {
        Family family = familyManager.getFamily(authorizedMember.getFamilyId());
        return GetFamilyInfoRes.of(family);
    }

    @Transactional(readOnly = true)
    public FamilyMemberInfo getFamilyMemberInfo(AuthorizedMember authorizedMember) {
        FamilyMember familyMember = familyManager.getFamilyMember(FamilyMemberId.of(authorizedMember));
        return FamilyMemberInfo.of(familyMember);
    }
}
