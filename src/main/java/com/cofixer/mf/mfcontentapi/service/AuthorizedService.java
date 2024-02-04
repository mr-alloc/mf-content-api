package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.dto.AuthorizedInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthorizedService {

    private static final String AUTHORIZED_KEY = "authorized";

    public static AuthorizedInfo getInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return (AuthorizedInfo) request.getAttribute(AUTHORIZED_KEY);
    }

    public static void setInfo(HttpServletRequest request, AuthorizedInfo authorizedInfo) {
        request.setAttribute(AUTHORIZED_KEY, authorizedInfo);
    }
}
