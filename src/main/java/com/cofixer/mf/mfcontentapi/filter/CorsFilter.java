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

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    private static final String ALLOWED_ORIGIN = "http://localhost:5173";
    private static final String ALLOWED_CREDENTIALS = "true";

    private static final String UserAgent = "User-Agent";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String userAgent = httpRequest.getHeader(UserAgent);
        log.info("User-Agent: {}", userAgent);

        if (!HttpMethod.OPTIONS.name().equals(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOWED_CREDENTIALS);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization");

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
