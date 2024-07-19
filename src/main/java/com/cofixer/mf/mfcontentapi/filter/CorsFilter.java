package com.cofixer.mf.mfcontentapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    private static final String ALLOWED_ORIGIN = "http://mf-service-web";
    private static final String ALLOWED_CREDENTIALS = "true";

    private static final String USER_AGENT = "User-Agent";
    private static final String ALLOWED_HEADERS = String.join(", ", List.of("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "Selected-Family-Id"));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String userAgent = httpRequest.getHeader(USER_AGENT);
        log.info("User-Agent: {}", userAgent);

        if (!HttpMethod.OPTIONS.name().equals(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }


        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOWED_CREDENTIALS);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");

        //CORS preflight request OK
        httpResponse.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
