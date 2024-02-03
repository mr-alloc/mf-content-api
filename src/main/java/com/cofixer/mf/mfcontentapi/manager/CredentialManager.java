package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.AccessCredential;
import com.cofixer.mf.mfcontentapi.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CredentialManager {

    private final CredentialRepository credentialRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void registerCredential(Long id, String credential) {
        credentialRepository.save(AccessCredential.of(id, credential));
    }
}
