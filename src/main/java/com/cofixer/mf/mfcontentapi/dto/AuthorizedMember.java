package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.constant.AccountRoleType;

public record AuthorizedMember(
        Long aid,
        Long mid,
        AccountRoleType role
) {

    public boolean hasMemberId() {
        return mid != null;
    }
}
