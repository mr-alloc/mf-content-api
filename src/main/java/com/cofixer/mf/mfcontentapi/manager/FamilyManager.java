package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember.FamilyMemberId;
import com.cofixer.mf.mfcontentapi.dto.res.FamilySummary;
import com.cofixer.mf.mfcontentapi.repository.FamilyMemberRepository;
import com.cofixer.mf.mfcontentapi.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FamilyManager {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public FamilyMember registerFamilyMember(Family family, Long mid, MemberRoleType memberRoleType) {
        FamilyMember familyMember = FamilyMember.forCreate(family.getId(), mid, memberRoleType);

        return familyMemberRepository.save(familyMember);
    }

    /**
     * 소속된 패밀리 멤버 목록
     *
     * @param memberIdd
     * @return
     */
    public List<FamilyMember> getFamilyMembers(Long memberIdd) {
        return familyMemberRepository.findByIdMemberId(memberIdd);
    }

    public List<FamilySummary> getOwnFamilies(Long mid) {
        return familyRepository.findOwnFamilies(mid);
    }

    public FamilyMember getFamilyMember(FamilyMemberId familyMemberId) {
        return familyMemberRepository.findById(familyMemberId)
                .orElseThrow(() -> new IllegalArgumentException("가족 멤버가 존재하지 않습니다."));
    }

    public List<FamilyMember> getAllMembers(Long familyId) {
        return familyMemberRepository.findByIdFamilyId(familyId);
    }
}
