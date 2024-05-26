package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.Anniversary;
import com.cofixer.mf.mfcontentapi.dto.AnniversarySearchFilter;
import com.cofixer.mf.mfcontentapi.repository.AnniversaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnniversaryManager {

    private final AnniversaryRepository anniversaryRepository;

    public List<Anniversary> getAnniversaries(AnniversarySearchFilter searchFilter) {
        return anniversaryRepository.getAnniversariesBySearchFilter(searchFilter);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Anniversary saveAnniversary(Anniversary newer) {
        return anniversaryRepository.save(newer);
    }
}
