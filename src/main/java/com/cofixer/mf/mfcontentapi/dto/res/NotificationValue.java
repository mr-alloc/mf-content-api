package com.cofixer.mf.mfcontentapi.dto.res;

import com.cofixer.mf.mfcontentapi.domain.SystemNotification;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알림 정보")
public record NotificationValue(

        @Schema(description = "알림 ID")
        Long id,

        @Schema(description = "알림 내용")
        String content,

        @Schema(description = "발송 일시")
        Long sentAt

) {

    public static NotificationValue of(SystemNotification notification) {
        return new NotificationValue(
                notification.getId(),
                notification.getContent(),
                notification.getCreatedAt()
        );
    }
}
