package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.domain.UserSetting;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserSettingManager {

    private final UserSettingRepository repository;


    @Transactional(propagation = Propagation.MANDATORY)
    public UserSetting saveUserSetting(UserSetting setting) {
        return repository.save(setting);
    }

    public UserSetting getUserSetting(Long memberId) {
        return repository.findById(memberId)
                .orElseThrow(() -> new MemberException(DeclaredMemberResult.NOT_FOUND_MEMBER));
    }
}
