package com.cofixer.mf.mfcontentapi.aspect;

import com.cofixer.mf.mfcontentapi.configuration.ServiceRoleProperty;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.RoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedInfo;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class CheckAspect {

    private final ServiceRoleProperty serviceRoleProperty;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerBean() {
    }


    @Before("controllerBean() && (@within(MemberAuth) || @annotation(MemberAuth))")
    public void checkMemberRole(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Map<Integer, RoleType> serviceRoleMap = serviceRoleProperty.getServiceRoleMap();

        //멤버 권한
        RoleType memberRole = Optional.ofNullable(AuthorizedService.getInfo())
                .map(AuthorizedInfo::role)
                .orElse(RoleType.GUEST);

        //필요한 최소 권한
        RoleType needRole = Optional.ofNullable(serviceRoleMap.get(method.hashCode()))
                .orElse(RoleType.ADMIN);

        log.info("Need Role [{}], member role is [{}]", needRole.name(), memberRole.name());
        if (memberRole.isAuthorizedThan(needRole)) {
            return;
        }

        throw new MemberException(DeclaredMemberResult.NOT_AUTHORIZED_ROLE, needRole.name());
    }
}
