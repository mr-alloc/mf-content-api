package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AuthorizedService {

    private static final String AUTHORIZED_KEY = "authorized";

    public static AuthorizedMember getMember() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(AuthorizedService::getMemberWithRequested)
                .orElse(null);
    }

    private static AuthorizedMember getMemberWithRequested(HttpServletRequest request) {
        return Optional.ofNullable(request.getAttribute(AUTHORIZED_KEY))
                .map(AuthorizedMember.class::cast)
                .orElse(null);
    }

    public static void setInfo(HttpServletRequest request, AuthorizedMember authorizedMember) {
        request.setAttribute(AUTHORIZED_KEY, authorizedMember);
    }
}
