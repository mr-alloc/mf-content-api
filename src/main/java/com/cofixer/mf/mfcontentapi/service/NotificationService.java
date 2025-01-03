package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.ContentType;
import com.cofixer.mf.mfcontentapi.constant.TargetType;
import com.cofixer.mf.mfcontentapi.domain.SystemNotification;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.res.NotificationValue;
import com.cofixer.mf.mfcontentapi.manager.SystemNotificationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final SystemNotificationManager manager;

    @Transactional
    public void notify(TargetType targetType, Long id, ContentType contentType, String content) {
        SystemNotification notification = SystemNotification.of(targetType, id, contentType, content);
        manager.saveNotification(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationValue> getNotifications(AuthorizedMember authorizedMember) {
        return null;
    }
}
