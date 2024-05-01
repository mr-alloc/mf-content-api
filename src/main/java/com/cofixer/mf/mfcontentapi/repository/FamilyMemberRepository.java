package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, FamilyMemberId> {

    List<FamilyMember> findByIdMemberId(Long memberId);

    List<FamilyMember> findByIdFamilyId(Long familyId);
}
