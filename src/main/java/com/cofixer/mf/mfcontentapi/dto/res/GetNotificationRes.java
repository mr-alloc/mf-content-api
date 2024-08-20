package com.cofixer.mf.mfcontentapi.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "알림 조회 정보")
public record GetNotificationRes(

        @Schema(description = "알림 목록")
        List<NotificationValue> notifications
) {
        public static GetNotificationRes of(List<NotificationValue> notifications) {
                return new GetNotificationRes(notifications);
        }
}
