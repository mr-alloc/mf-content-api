package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_preflight_tester")
public class PreflightTester {

    @Id
    @Comment("테스터 이메일")
    @Column(name = "reserved_email")
    String reservedEmail;

    @Comment("가입 일시")
    @Column(name = "joined_at")
    Long joinedAt;

    public boolean isNotJoined() {
        return joinedAt == null;
    }

    public void recordJoinedAt() {
        this.joinedAt = TemporalUtil.getEpochSecond();
    }
}
