package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.ContentType;
import com.cofixer.mf.mfcontentapi.constant.TargetType;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_system_notification")
public class SystemNotification implements Serializable {

    @Serial
    private static final long serialVersionUID = 3724396103300416787L;

    @Id
    Long id;

    @Comment("알림 타입 0: 유저, 1: 패밀리, 2: 시스템")
    Integer type;

    @Comment("type 컬럼에 대한 테이블 아이디")
    @Column(name = "target_id")
    Long targetId;

    @Comment("내용 타입")
    @Column(name = "content_type")
    Integer contentType;

    @Comment("내용")
    String content;

    @Comment("생성일시")
    @Column(name = "created_at")
    Long createdAt;

    public static SystemNotification of(TargetType type, Long targetId, ContentType contentType, String content) {
        SystemNotification noti = new SystemNotification();
        noti.type = type.getValue();
        noti.targetId = targetId;
        noti.contentType = contentType.getValue();
        noti.content = content;
        noti.createdAt = TemporalUtil.getEpochSecond();

        return noti;
    }
}
