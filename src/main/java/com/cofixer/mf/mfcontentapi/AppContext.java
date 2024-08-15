package com.cofixer.mf.mfcontentapi;

import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.ZoneOffset;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppContext {

    public static final Clock APP_CLOCK = Clock.systemUTC();
    public static final ZoneOffset APP_ZONE_OFFSET = ZoneOffset.UTC;
    public static final ZoneOffset APP_ZONE_OFFSET_KST = ZoneOffset.ofHours(9);
    //토근 만료 시간
    public static final long ACCESS_TOKEN_EXPIRE_SECOND = TemporalUtil.WEEK_IN_SECONDS;
    public static final long REFRESH_TOKEN_EXPIRE_SECOND = TemporalUtil.DAYS30_IN_SECONDS;
}
