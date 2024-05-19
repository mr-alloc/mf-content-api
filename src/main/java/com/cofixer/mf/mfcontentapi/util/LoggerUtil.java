package com.cofixer.mf.mfcontentapi.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class LoggerUtil {

    public static String log(String message) {
        HttpServletRequest request = RequestUtil.getRequest();
        return String.format("[%s %s] %s", request.getMethod(), request.getRequestURI(), message);
    }
}
