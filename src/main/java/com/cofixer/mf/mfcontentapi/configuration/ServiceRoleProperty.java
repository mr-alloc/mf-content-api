package com.cofixer.mf.mfcontentapi.configuration;

import com.cofixer.mf.mfcontentapi.aspect.MemberAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class ServiceRoleProperty {

    /**
     * 요청 Path 별 권한 설정
     */
    @Getter
    private Map<Integer, AccountRoleType> serviceRoleMap;

    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    public ServiceRoleProperty(
            @Qualifier("requestMappingHandlerMapping")
            RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        this.serviceRoleMap = new HashMap<>();
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        scanControllers();
    }

    private void scanControllers() {
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            try {
                Method method = value.getMethod();
                MemberAuth defaultControllerAuth = AnnotationUtils.getAnnotation(method.getDeclaringClass(), MemberAuth.class);
                MemberAuth methodAuth = AnnotationUtils.getAnnotation(method, MemberAuth.class);

                MemberAuth memberAuth = getMemberAuthWithOrdered(defaultControllerAuth, methodAuth);

                if (memberAuth != null) {
                    Assert.isTrue(!serviceRoleMap.containsKey(method.hashCode()), "중복된 메소드가 존재합니다.");
                    serviceRoleMap.put(method.hashCode(), memberAuth.value());
                }
            } catch (Exception e) {
                log.error("{} {}", key, e.getMessage());
            }
        });
    }

    private MemberAuth getMemberAuthWithOrdered(MemberAuth controllerMemberAuth, MemberAuth methodMemberAuth) {
        return methodMemberAuth != null ? methodMemberAuth : controllerMemberAuth;
    }
}
