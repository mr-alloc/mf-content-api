package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeviceType;
import com.cofixer.mf.mfcontentapi.domain.AccessCredential;
import com.cofixer.mf.mfcontentapi.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CredentialManager {

    private final CredentialRepository credentialRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void registerCredential(Long id, Integer deviceCode, String latestAccessToken, String credential) {
        DeviceType deviceType = DeviceType.fromCode(deviceCode);
        credentialRepository.save(AccessCredential.of(id, deviceType, credential, latestAccessToken));
    }

    public Optional<AccessCredential> getMayCredential(Long accountId) {
        return credentialRepository.findById(accountId);
    }

    public AccessCredential getCredential(DeviceType deviceType, String refreshToken) {
        return credentialRepository.findByDeviceTypeAndCredential(deviceType.getCode(), refreshToken);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateAccessToken(AccessCredential credential, String newAccessToken) {
        credential.updateAccessToken(newAccessToken);
    }
}
