package com.cofixer.mf.mfcontentapi;

import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.ZoneOffset;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppContext {

    public static final Clock APP_CLOCK = Clock.systemUTC();
    public static final ZoneOffset APP_ZONE_OFFSET = ZoneOffset.UTC;
    public static final ZoneOffset APP_ZONE_OFFSET_KST = ZoneOffset.ofHours(9);
    //토근 만료 시간
    public static final long ACCESS_TOKEN_EXPIRE_SECOND = 3600;
    public static final long REFRESH_TOKEN_EXPIRE_SECOND = 86400;
}
