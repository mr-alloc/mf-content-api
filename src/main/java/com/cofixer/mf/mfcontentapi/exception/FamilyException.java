package com.cofixer.mf.mfcontentapi.exception;


import com.cofixer.mf.mfcontentapi.constant.DeclaredFamilyResult;

public class FamilyException extends CommonException {

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
