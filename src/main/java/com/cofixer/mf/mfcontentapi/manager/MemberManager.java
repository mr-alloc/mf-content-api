package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberManager {

    private final MemberRepository memberRepository;

    public Optional<Member> getMayMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> getMayMemberByAccountId(long accountId) {
        return memberRepository.findByAccountId(accountId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Member createMember(Long accountId) {
        Member member = Member.forCreate(accountId);
        return memberRepository.save(member);
    }
}
