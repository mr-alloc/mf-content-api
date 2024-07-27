package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.constant.*;
import com.cofixer.mf.mfcontentapi.dto.res.CommonErrorRes;
import com.cofixer.mf.mfcontentapi.util.ConditionUtil;
import com.cofixer.mf.mfcontentapi.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice(basePackages = "com.cofixer.mf.mfcontentapi.controller")
public class DefaultControllerAdvice {

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<CommonErrorRes> missionExceptionHandler(MissionException ex) {
        DeclaredMissionResult result = ex.getResult();
        log.error(LoggerUtil.logWithLastTrace(ex));
        return Optional.ofNullable(result.getHttpStatus())
                .map(ResponseEntity::status)
                .orElseGet(ResponseEntity::internalServerError)
                .body(new CommonErrorRes("MISSION", result.getCode()));
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<CommonErrorRes> accountExceptionHandler(AccountException ex) {
        DeclaredAccountResult result = ex.getResult();
        log.error(LoggerUtil.logWithLastTrace(ex));
        return Optional.ofNullable(result.getHttpStatus())
                .map(ResponseEntity::status)
                .orElseGet(ResponseEntity::internalServerError)
                .body(new CommonErrorRes("ACCOUNT", result.getCode()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<CommonErrorRes> memberExceptionHandler(MemberException ex) {
        DeclaredMemberResult result = ex.getResult();
        log.error(LoggerUtil.logWithLastTrace(ex));

        return Optional.ofNullable(result.getHttpStatus())
                .map(ResponseEntity::status)
                .map(bodyBuilder -> ConditionUtil.evaluate(result.isBlockMember(), bodyBuilder,
                        builder -> builder.header(UserProtocol.USER_STATUS, MemberStatus.BLOCKED.getCode())))
                .orElseGet(ResponseEntity::internalServerError)
                .body(new CommonErrorRes("MEMBER", result.getCode()));
    }

    @ExceptionHandler(FamilyException.class)
    public ResponseEntity<CommonErrorRes> familyExceptionHandler(FamilyException ex) {
        DeclaredFamilyResult result = ex.getResult();
        log.error(LoggerUtil.logWithLastTrace(ex));
        return Optional.ofNullable(result.getHttpStatus())
                .map(ResponseEntity::status)
                .orElseGet(ResponseEntity::internalServerError)
                .body(new CommonErrorRes("FAMILY", result.getCode()));
    }
}
