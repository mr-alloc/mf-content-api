package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(DeclaredMemberResult.NOT_FOUND_MEMBER));
    }

    public List<Member> getMembers(Collection<Long> memberIds) {
        return memberRepository.findAllById(memberIds);
    }

    public boolean existMember(Long memberId) {
        return memberRepository.existsById(memberId);
    }
}
