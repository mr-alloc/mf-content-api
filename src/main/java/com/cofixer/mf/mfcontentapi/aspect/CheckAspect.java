package com.cofixer.mf.mfcontentapi.aspect;

import com.cofixer.mf.mfcontentapi.configuration.ServiceRoleProperty;
import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.DeclaredValidateResult;
import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.dto.req.ValidatableRequest;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.exception.ValidateException;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
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


    @Before("controllerBean() && (@within(com.cofixer.mf.mfcontentapi.aspect.AccountAuth) || @annotation(com.cofixer.mf.mfcontentapi.aspect.AccountAuth))")
    public void checkAccountRole(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Map<Integer, AccountRoleType> serviceRoleMap = serviceRoleProperty.getServiceRoleMap();
        if (!serviceRoleMap.containsKey(method.hashCode())) {
            return;
        }

        //멤버 권한
        AccountRoleType memberRole = Optional.ofNullable(AuthorizedService.getMember())
                .map(AuthorizedMember::getAccountRole)
                .orElse(AccountRoleType.GUEST);

        //필요한 최소 권한
        AccountRoleType needRole = Optional.ofNullable(serviceRoleMap.get(method.hashCode()))
                .orElse(AccountRoleType.ADMIN);

        log.info("Need Role [{}], member role is [{}]", needRole.name(), memberRole.name());
        if (memberRole.isNotAllowedTo(needRole)) {
            throw new MemberException(DeclaredMemberResult.NOT_ALLOWED_ROLE, needRole.name());
        }
    }

    @Before("controllerBean() && (@within(FamilyMemberAuth) || @annotation(FamilyMemberAuth))")
    public void checkMemberRole(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Map<Integer, MemberRoleType> memberRoleMap = serviceRoleProperty.getMemberRoleMap();
        if (!memberRoleMap.containsKey(method.hashCode())) {
            return;
        }

        //멤버 권한
        MemberRoleType memberRole = Optional.ofNullable(AuthorizedService.getMember())
                .map(AuthorizedMember::getMemberRole)
                .orElse(MemberRoleType.NONE);

        //필요한 최소 권한
        MemberRoleType needRole = Optional.ofNullable(memberRoleMap.get(method.hashCode()))
                .orElse(MemberRoleType.MASTER);

        log.info("Need Family Role [{}], member role is [{}]", needRole.name(), memberRole.name());
        if (memberRole.isNotAllowedTo(needRole)) {
            throw new MemberException(DeclaredMemberResult.NOT_ALLOWED_ROLE, needRole.name());
        }

    }

    @Before("controllerBean()")
    public void checkAllController(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();

        // 파라미터 확인
        for (Object argument : arguments) {

            //ValidatableRequest 인터페이스를 구현한 객체인 경우 validate 메소드를 호출
            if (argument instanceof ValidatableRequest<?> validatableRequest) {
                validatableRequest.validate(() -> new ValidateException(DeclaredValidateResult.FAILED_AT_COMMON_VALIDATION));
                log.info("[{}] was validated", signature.getName());
            }
        }
    }

}
