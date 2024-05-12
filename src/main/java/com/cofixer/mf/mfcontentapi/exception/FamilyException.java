package com.cofixer.mf.mfcontentapi.exception;


import com.cofixer.mf.mfcontentapi.constant.DeclaredFamilyResult;
import lombok.Getter;

import java.io.Serial;

@Getter
public class FamilyException extends CommonException {

    @Serial
    private static final long serialVersionUID = 7504217890899463643L;

    private final DeclaredFamilyResult result;

    public FamilyException(DeclaredFamilyResult result) {
        super(result.name());
        this.result = result;
    }

    @Override
    public int getCode() {
        return this.result.getCode();
    }
}
