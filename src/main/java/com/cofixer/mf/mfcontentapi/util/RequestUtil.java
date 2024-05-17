package com.cofixer.mf.mfcontentapi.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RequestUtil {

    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }

    public static String getRemoteAddr() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getRemoteAddr)
                .orElse(null);
    }

    public static String getRemoteHost() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getRemoteHost)
                .orElse(null);
    }

    public static Integer getRemotePort() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getRemotePort)
                .orElse(null);
    }

    public static String getRemoteUser() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getRemoteUser)
                .orElse(null);
    }

    public static String getLocalAddr() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getLocalAddr)
                .orElse(null);
    }

    public static String getLocalName() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getLocalName)
                .orElse(null);
    }

    public static Integer getLocalPort() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getLocalPort)
                .orElse(null);
    }

    public static String getServerName() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getServerName)
                .orElse(null);
    }

    public static Integer getServerPort() {
        return Optional.ofNullable(RequestUtil.getRequest())
                .map(HttpServletRequest::getServerPort)
                .orElse(null);
    }
}
