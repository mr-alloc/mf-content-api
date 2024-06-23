package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.AccessCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<AccessCredential, Long> {

    AccessCredential findByDeviceTypeAndCredential(Integer deviceType, String credential);


}
