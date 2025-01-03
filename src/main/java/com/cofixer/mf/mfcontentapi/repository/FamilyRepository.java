package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.repository.query.FamilyQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>, FamilyQueryRepository {

    boolean existsByInviteCode(String inviteCode);

    Optional<Family> findByInviteCode(String inviteCode);
}
