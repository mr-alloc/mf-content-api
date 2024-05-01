package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredFamilyResult;
import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;
import com.cofixer.mf.mfcontentapi.dto.res.FamilySummary;
import com.cofixer.mf.mfcontentapi.exception.FamilyException;
import com.cofixer.mf.mfcontentapi.repository.FamilyMemberConnectRequestRepository;
import com.cofixer.mf.mfcontentapi.repository.FamilyMemberRepository;
import com.cofixer.mf.mfcontentapi.repository.FamilyRepository;
import com.cofixer.mf.mfcontentapi.util.ConditionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FamilyManager {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository memberRepository;
    private final FamilyMemberConnectRequestRepository connectRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMember registerFamilyMember(FamilyMemberId familyMemberId, MemberRoleType memberRoleType) {
        FamilyMember familyMember = FamilyMember.forCreate(familyMemberId, memberRoleType);

        return memberRepository.save(familyMember);
    }

    /**
     * 소속된 패밀리 멤버 목록
     *
     * @param memberIdd
     * @return
     */
    public List<FamilyMember> getFamilyMembers(Long memberId) {
        return memberRepository.findByIdMemberId(memberId);
    }

    public List<FamilySummary> getOwnFamilies(Long mid) {
        return familyRepository.findOwnFamilies(mid);
    }

    public FamilyMember getFamilyMember(FamilyMemberId familyMemberId) {
        return memberRepository.findById(familyMemberId)
                .orElseThrow(() -> new IllegalArgumentException("가족 멤버가 존재하지 않습니다."));
    }

    public List<FamilyMember> getAllMembers(Long familyId) {
        return memberRepository.findByIdFamilyId(familyId);
    }

    public boolean isOurFamily(FamilyMemberId familyMemberId) {
        return memberRepository.existsById(familyMemberId);
    }

    public Family getFamily(Long familyId) {
        return familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyException(DeclaredFamilyResult.NOT_FOUND_FAMILY));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMemberConnectRequest requestToConnect(FamilyMemberId familyMemberId, FamilyMemberDirection direction) {
        FamilyMemberConnectRequest.Id id = FamilyMemberConnectRequest.Id.of(familyMemberId, direction);
        if (connectRepository.hasRequested(id)) {
            throw new FamilyException(DeclaredFamilyResult.ALREADY_REQUESTED_TO_CONNECT);
        }
        FamilyMemberConnectRequest request = FamilyMemberConnectRequest.of(id);

        return connectRepository.save(request);

    }

    public List<FamilyMemberConnectRequest> getConnectRequests(FamilyMemberId familyMemberId, FamilyMemberDirection direction) {
        return connectRepository.getConnectRequests(familyMemberId, direction);
    }

    public FamilyMemberConnectRequest getConnectRequest(FamilyMemberId familyMemberId, FamilyMemberDirection familyMemberDirection) {
        return connectRepository.getConnectRequest(FamilyMemberConnectRequest.Id.of(familyMemberId, familyMemberDirection));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMember acceptRequestAndGetNewMember(FamilyMemberConnectRequest requested) {
        FamilyMemberId familyMemberId = requested.getFamilyMemberId();
        ConditionUtil.throwIfTrue(memberRepository.existsById(familyMemberId),
                () -> new FamilyException(DeclaredFamilyResult.ALREADY_OUR_FAMILY_MEMBER));

        FamilyMember newMember = memberRepository.save(FamilyMember.forCreate(familyMemberId, MemberRoleType.REGULAR));
        connectRepository.deleteAllRequests(requested.getFamilyId(), requested.getMemberId());

        return newMember;
    }

    public List<Family> getFamilies(Collection<Long> familyIds) {
        return familyRepository.findAllById(familyIds);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void cancelRequest(FamilyMemberConnectRequest connectRequest) {
        connectRepository.delete(connectRequest);
    }

    public boolean existFamily(Long familyId) {
        return familyRepository.existsById(familyId);
    }
}
