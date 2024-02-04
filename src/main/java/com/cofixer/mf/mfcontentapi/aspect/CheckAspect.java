package com.cofixer.mf.mfcontentapi.aspect;

import com.cofixer.mf.mfcontentapi.configuration.ServiceRoleProperty;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.RoleType;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@RequiredArgsConstructor
@Aspect
@Component
public class CheckAspect {

    private final ServiceRoleProperty serviceRoleProperty;

    @Before("@annotation(MemberAuth)")
    public void checkMemberRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Map<Integer, RoleType> serviceRoleMap = serviceRoleProperty.getServiceRoleMap();

        if (!serviceRoleMap.containsKey(method.hashCode())) {
            return;
        }

        RoleType memberRole = AuthorizedService.getInfo().role();

        if (memberRole.isAuthorizedThan(serviceRoleMap.get(method.hashCode()))) {
            return;
        }

        throw new MemberException(DeclaredMemberResult.NOT_AUTHORIZED_ROLE, "Not authorized role.");
    }
}
