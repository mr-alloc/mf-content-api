package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.dto.req.ChangeNicknameReq;
import com.cofixer.mf.mfcontentapi.dto.res.SimpleMemberInfo;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
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
    private final MemberManager manager;
    private final CommonValidator commonValidator;
    private final EntityPrinter printer;
    private final ExpandableEntitySetting es;

    @Transactional(readOnly = true)
    public SimpleMemberInfo getSimpleMemberInfo(Long mid) {
        Member member = manager.getMember(mid);

        log.info(printer.drawEntity(member, es.getConfig()));

        return new SimpleMemberInfo(member.getId(), member.getNickname(), member.getRole());
    }

    @Transactional
    public void changeNickname(Long mid, ChangeNicknameReq req) {
        commonValidator.validateNickname(req.getNickname());
        Member member = manager.getMember(mid);

        if (member.hasNickName()) {
            throw new MemberException(DeclaredMemberResult.ALREADY_INITIALIZED);
        }

        member.changeNickname(req.getNickname());
    }
}
