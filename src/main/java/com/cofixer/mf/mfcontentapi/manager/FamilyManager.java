package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.MemberRole;
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
    public FamilyMember registerFamilyMember(Family family, Long mid, MemberRole memberRole) {
        FamilyMember familyMember = FamilyMember.forCreate(family.getId(), mid, memberRole);

        return familyMemberRepository.save(familyMember);
    }

    public List<FamilyMember> getFamilyMembers(Long mid) {
        return familyMemberRepository.findByIdMemberId(mid);
    }

    public List<FamilySummary> getOwnFamilies(Long mid) {
        return familyRepository.findOwnFamilies(mid);
    }

    public FamilyMember getFamilyMember(FamilyMemberId familyMemberId) {
        return familyMemberRepository.findById(familyMemberId)
                .orElseThrow(() -> new IllegalArgumentException("가족 멤버가 존재하지 않습니다."));
    }
}
