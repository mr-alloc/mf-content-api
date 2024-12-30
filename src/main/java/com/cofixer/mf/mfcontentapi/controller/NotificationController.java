package com.cofixer.mf.mfcontentapi.controller;

import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.res.GetNotificationRes;
import com.cofixer.mf.mfcontentapi.dto.res.NotificationValue;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "/v1/notification", description = "알림")
@RequestMapping("/v1/notification")
public class NotificationController {

    private final NotificationService service;

    @GetMapping
    @Operation(summary = "조회")
    public ResponseEntity<GetNotificationRes> getNotifications() {
        AuthorizedMember authorizedMember = AuthorizedService.getMember();

        List<NotificationValue> notifications = service.getNotifications(authorizedMember);

        return ResponseEntity.ok(GetNotificationRes.of(notifications));
    }
}
