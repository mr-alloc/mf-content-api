package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.MemberRole;
import com.cofixer.mf.mfcontentapi.domain.Family;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyReq;
import com.cofixer.mf.mfcontentapi.dto.res.FamilySummary;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyService {

    private final CommonValidator commonValidator;
    private final FamilyManager familyManager;

    @Transactional
    public Family createFamily(CreateFamilyReq req, Long mid) {
        Family newer = Family.forCreate(req, mid);
        commonValidator.validateFamily(newer);

        Family saved = familyManager.saveFamily(newer);
        familyManager.registerFamilyMember(saved, mid, MemberRole.MEMBER);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<FamilySummary> getFamilySummaries(Long mid) {
        return familyManager.getOwnFamilies(mid);
    }
}
