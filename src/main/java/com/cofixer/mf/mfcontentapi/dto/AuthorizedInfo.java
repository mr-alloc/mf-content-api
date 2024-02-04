package com.cofixer.mf.mfcontentapi.dto;

import com.cofixer.mf.mfcontentapi.constant.RoleType;

public record AuthorizedInfo(
        Long aid,
        Long mid,
        RoleType role
) {

    public boolean hasMemberId() {
        return mid != null;
    }
}
