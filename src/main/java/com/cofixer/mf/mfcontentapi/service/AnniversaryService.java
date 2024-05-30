package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.AnniversaryType;
import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.cofixer.mf.mfcontentapi.dto.AnniversarySearchFilter;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAnniversaryReq;
import com.cofixer.mf.mfcontentapi.dto.res.AnniversaryValue;
import com.cofixer.mf.mfcontentapi.manager.AnniversaryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnniversaryService {

    private final AnniversaryManager anniversaryManager;

    @Transactional(readOnly = true)
    public List<AnniversaryValue> getAnniversaries(String yearMonth, AuthorizedMember authorizedMember) {
        AnniversarySearchFilter searchFilter = AnniversarySearchFilter.of(authorizedMember, yearMonth);
        return anniversaryManager.getAnniversaries(searchFilter).stream()
                .map(AnniversaryValue::of)
                .toList();
    }

    @Transactional
    public AnniversaryValue createAnniversary(CreateAnniversaryReq req, AuthorizedMember authorizedMember) {
        Anniversary newer = switch (AnniversaryType.fromValue(req.type())) {
            case PERIOD -> Anniversary.forPeriod(authorizedMember, req);
            case SINGLE, MULTIPLE -> Anniversary.forMultiple(authorizedMember, req);
        };

        return AnniversaryValue.of(anniversaryManager.saveAnniversary(newer));
    }
}
