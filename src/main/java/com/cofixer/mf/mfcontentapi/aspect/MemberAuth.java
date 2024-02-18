package com.cofixer.mf.mfcontentapi.aspect;

import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MemberAuth {

    AccountRoleType value() default AccountRoleType.MEMBER;

    AccountRoleType[] specify() default {};


}
