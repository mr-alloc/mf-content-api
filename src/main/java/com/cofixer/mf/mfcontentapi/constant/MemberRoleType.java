package com.cofixer.mf.mfcontentapi.constant;

import com.cofixer.mf.mfcontentapi.util.IterateUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {
    NONE(-1),
    REGULAR(0),
    SUB_MASTER(1),
    MASTER(2);

    private static final Map<Integer, MemberRoleType> CACHED = IterateUtil.toMap(List.of(MemberRoleType.values()), MemberRoleType::getLevel);
    private final int level;


    public static MemberRoleType fromLevel(Integer level) {
        return CACHED.getOrDefault(level, NONE);
    }

    public boolean isNotAllowedTo(MemberRoleType needRole) {
        return this.level < needRole.level;
    }
}