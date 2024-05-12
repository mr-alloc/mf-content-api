package com.cofixer.mf.mfcontentapi.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
public class FamilyJoinReq {

    @Pattern(regexp = "^[a-f0-9]{32}$")
    String inviteCode;

    @NotNull
    String introduce;
}
