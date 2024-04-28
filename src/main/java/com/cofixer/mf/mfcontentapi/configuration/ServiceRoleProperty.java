package com.cofixer.mf.mfcontentapi.configuration;

import com.cofixer.mf.mfcontentapi.aspect.AccountAuth;
import com.cofixer.mf.mfcontentapi.aspect.FamilyMemberAuth;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
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

    @Getter
    private Map<Integer, MemberRoleType> memberRoleMap;

    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    public ServiceRoleProperty(
            @Qualifier("requestMappingHandlerMapping")
            RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        this.serviceRoleMap = new HashMap<>();
        this.memberRoleMap = new HashMap<>();
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        scanControllers();
    }

    private void scanControllers() {
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            try {
                Method method = value.getMethod();
                AccountAuth defaultControllerAccountAuth = AnnotationUtils.getAnnotation(method.getDeclaringClass(), AccountAuth.class);
                AccountAuth methodAuth = AnnotationUtils.getAnnotation(method, AccountAuth.class);

                AccountAuth accountAuth = getAccountAuthWithOrdered(defaultControllerAccountAuth, methodAuth);

                if (accountAuth != null) {
                    Assert.isTrue(!serviceRoleMap.containsKey(method.hashCode()), "중복된 메소드가 존재합니다.");
                    serviceRoleMap.put(method.hashCode(), accountAuth.value());
                }

                FamilyMemberAuth defaultControllerMemberAuth = AnnotationUtils.getAnnotation(method.getDeclaringClass(), FamilyMemberAuth.class);
                FamilyMemberAuth methodMemberAuth = AnnotationUtils.getAnnotation(method, FamilyMemberAuth.class);

                FamilyMemberAuth memberAuth = getMemberAuthWithOrdered(defaultControllerMemberAuth, methodMemberAuth);

                if (memberAuth != null) {
                    Assert.isTrue(!memberRoleMap.containsKey(method.hashCode()), "중복된 메소드가 존재합니다.");
                    memberRoleMap.put(method.hashCode(), memberAuth.value());
                }
            } catch (Exception e) {
                log.error("{} {}", key, e.getMessage());
            }
        });
    }

    private FamilyMemberAuth getMemberAuthWithOrdered(FamilyMemberAuth defaultControllerMemberAuth, FamilyMemberAuth methodMemberAuth) {
        return methodMemberAuth != null ? methodMemberAuth : defaultControllerMemberAuth;
    }

    private AccountAuth getAccountAuthWithOrdered(AccountAuth controllerAccountAuth, AccountAuth methodAccountAuth) {
        return methodAccountAuth != null ? methodAccountAuth : controllerAccountAuth;
    }
}
