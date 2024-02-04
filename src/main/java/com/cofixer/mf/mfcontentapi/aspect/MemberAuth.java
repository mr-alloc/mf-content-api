package com.cofixer.mf.mfcontentapi.aspect;

import com.cofixer.mf.mfcontentapi.constant.RoleType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MemberAuth {

    RoleType value() default RoleType.MEMBER;

    RoleType[] specify() default {};


}
