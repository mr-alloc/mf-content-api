package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.*;
import com.cofixer.mf.mfcontentapi.dto.res.CommonErrorRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.cofixer.mf.mfcontentapi.controller")
public class DefaultControllerAdvice {

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<CommonErrorRes> missionExceptionHandler(MissionException ex) {
        return ResponseEntity.internalServerError()
                .body(new CommonErrorRes("MISSION", ex.getCode()));
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<CommonErrorRes> accountExceptionHandler(AccountException ex) {
        DeclaredAccountResult result = ex.getResult();
        log.error("[{}] {}", result.name(), ex.getMessage());

        HttpStatus httpStatus = result.getHttpStatus();
        if (httpStatus != null) {
            return ResponseEntity.status(httpStatus)
                    .body(new CommonErrorRes("ACCOUNT", result.getCode()));
        }

        return ResponseEntity.internalServerError()
                .body(new CommonErrorRes("ACCOUNT", result.getCode()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<CommonErrorRes> memberExceptionHandler(MemberException ex) {
        DeclaredMemberResult result = ex.getResult();
        log.error("[{}] {}", result.name(), ex.getMessage());
        HttpStatus httpStatus = result.getHttpStatus();

        if (httpStatus != null) {
            if (ex.getResult().isBlockMember()) {
                return ResponseEntity.status(httpStatus)
                        .header(UserProtocol.USER_STATUS, MemberStatus.BLOCKED.getCode())
                        .body(new CommonErrorRes("MEMBER", result.getCode()));
            }

            return ResponseEntity.status(httpStatus)
                    .body(new CommonErrorRes("MEMBER", result.getCode()));
        }

        return ResponseEntity.internalServerError()
                .body(new CommonErrorRes("MEMBER", result.getCode()));
    }

    @ExceptionHandler(FamilyException.class)
    public ResponseEntity<CommonErrorRes> familyExceptionHandler(FamilyException ex) {
        DeclaredFamilyResult result = ex.getResult();
        log.error("[{}] {}", result.name(), ex.getMessage());
        HttpStatus httpStatus = result.getHttpStatus();

        if (httpStatus != null) {
            return ResponseEntity.status(httpStatus)
                    .body(new CommonErrorRes("FAMILY", result.getCode()));
        }

        return ResponseEntity.internalServerError()
                .body(new CommonErrorRes("FAMILY", result.getCode()));
    }
}
