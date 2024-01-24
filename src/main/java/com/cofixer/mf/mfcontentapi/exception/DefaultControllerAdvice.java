package com.cofixer.mf.mfcontentapi.exception;

import com.cofixer.mf.mfcontentapi.dto.res.CommonErrorRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cofixer.mf.mfcontentapi.controller")
public class DefaultControllerAdvice {

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<CommonErrorRes> missionExceptionHandler(MissionException ex) {
        return ResponseEntity.internalServerError()
                .body(new CommonErrorRes(ex.getMessage()));
    }
}
