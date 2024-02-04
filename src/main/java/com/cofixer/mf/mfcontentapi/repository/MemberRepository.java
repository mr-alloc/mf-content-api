package com.cofixer.mf.mfcontentapi.repository;


import com.cofixer.mf.mfcontentapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountId(long accountId);
}
