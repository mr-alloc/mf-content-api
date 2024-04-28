package com.cofixer.mf.mfcontentapi.aspect;

import com.cofixer.mf.mfcontentapi.constant.MemberRoleType;
import org.springframework.core.annotation.AliasFor;

public @interface FamilyMemberAuth {

    @AliasFor("minRole")
    MemberRoleType value() default MemberRoleType.MASTER;

    @AliasFor("value")
    MemberRoleType minRole() default MemberRoleType.MASTER;
}
