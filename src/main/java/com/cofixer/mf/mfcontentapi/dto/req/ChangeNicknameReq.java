package com.cofixer.mf.mfcontentapi.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangeNicknameReq {

    @NotEmpty
    String nickname;
}
