package com.cofixer.mf.mfcontentapi.configuration;

import com.cofixer.mf.mfcontentapi.interceptor.AuthenticateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthenticateInterceptor authenticateInterceptor;
    private static final String CREATE_ACCOUNT = "/v1/account/create";
    private static final String VERIFY_ACCOUNT = "/v1/account/verify";
    private static final String CONFIRM_ACCOUNT = "/v1/account/confirm";
    private static final String REFRESH_TOKEN = "/v1/account/refresh";
    private static final String OPENAPI_DOCS = "/v3/api-docs/**";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticateInterceptor)
                .excludePathPatterns(CREATE_ACCOUNT, VERIFY_ACCOUNT, CONFIRM_ACCOUNT, REFRESH_TOKEN, OPENAPI_DOCS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://mimily.dev.zibi.co")
                .exposedHeaders("User-Status");
    }
}
