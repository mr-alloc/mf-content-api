package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.dto.res.MemberDetailRes;
import com.cofixer.mf.mfcontentapi.dto.res.SimpleMemberInfoRes;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.AccountManager;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            FamilyMember familyMember = familyManager.getFamilyMember(FamilyMember.FamilyMemberId.of(authorizedMember));
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
        Member member = manager.getMember(authorizedMember.getMemberId());

        return MemberDetailRes.of(found, member);
    }
}
