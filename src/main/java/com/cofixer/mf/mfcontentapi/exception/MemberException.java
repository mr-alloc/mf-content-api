package com.cofixer.mf.mfcontentapi.exception;


import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import lombok.Getter;

import java.io.Serial;

@Getter
public class MemberException extends CommonException {

    @Serial
    private static final long serialVersionUID = 7429457279689155858L;

    private final DeclaredMemberResult result;

    public MemberException(DeclaredMemberResult result) {
        super(result.name());
        this.result = result;
    }

    public MemberException(DeclaredMemberResult result, String message) {
        super(message);
        this.result = result;
    }

    @Override
    public int getCode() {
        return this.result.getCode();
    }
}
