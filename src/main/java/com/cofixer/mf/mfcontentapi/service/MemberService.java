package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.dto.res.SimpleMemberInfo;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberManager manager;
    private final EntityPrinter printer;
    private final ExpandableEntitySetting es;

    public SimpleMemberInfo getSimpleMemberInfo(Long mid) {
        Member member = manager.getMayMember(mid)
                .orElseThrow(() -> new MemberException(DeclaredMemberResult.NOT_FOUND_MEMBER));

        log.info(printer.drawEntity(member, es.getConfig()));

        return new SimpleMemberInfo(member.getId(), member.getNickname(), member.getRole());
    }
}
