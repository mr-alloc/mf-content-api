package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.util.CollectionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@Getter
@RequiredArgsConstructor
public enum AccountRoleType {
    GUEST(0),
    MEMBER(1),
    ADMIN(2);
    private final int level;
    private static final Map<Integer, AccountRoleType> CACHED = CollectionUtil.toMap(List.of(AccountRoleType.values()), AccountRoleType::getLevel);


    public static boolean exist(int level) {
        return CACHED.containsKey(level);
    }

    public static AccountRoleType of(int level) {
        return CACHED.get(level);
    }

    public boolean isNotAllowedTo(AccountRoleType other) {
        return this.level < other.level;
    }
}
