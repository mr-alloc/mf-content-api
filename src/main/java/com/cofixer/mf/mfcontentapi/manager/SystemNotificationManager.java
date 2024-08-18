package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.SystemNotification;
import com.cofixer.mf.mfcontentapi.repository.SystemNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SystemNotificationManager {

    private final SystemNotificationRepository repository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveNotification(SystemNotification notification) {
        repository.save(notification);
    }

}
