package com.cofixer.mf.mfcontentapi.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRes {

    @JsonInclude(Include.NON_NULL)
    String message;
    public static ErrorRes of(String message) {
        return new ErrorRes(message);
    }
}
