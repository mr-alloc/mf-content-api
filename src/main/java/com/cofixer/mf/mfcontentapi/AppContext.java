package com.cofixer.mf.mfcontentapi;

import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.ZoneOffset;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppContext {

    public static final Clock APP_CLOCK = Clock.systemUTC();
    public static final ZoneOffset APP_ZONE_OFFSET = ZoneOffset.UTC;
    //토근 만료 시간
    public static final long CREDENTIAL_EXPIRE_SECOND = 7200;
}
