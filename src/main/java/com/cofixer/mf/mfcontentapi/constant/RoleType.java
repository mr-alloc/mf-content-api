package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.util.IterateUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@Getter
@RequiredArgsConstructor
public enum RoleType {
    GUEST(0),
    MEMBER(1),
    SUB_LEADER(2),
    LEADER(3),
    ADMIN(4);
    private final int level;
    private static final Map<Integer, RoleType> CACHED = IterateUtil.toMap(List.of(RoleType.values()), RoleType::getLevel);


    public static boolean exist(int level) {
        return CACHED.containsKey(level);
    }

    public static RoleType of(int level) {
        return CACHED.get(level);
    }

    public boolean isAuthorizedThan(RoleType other) {
        return this.level >= other.level;
    }
}
