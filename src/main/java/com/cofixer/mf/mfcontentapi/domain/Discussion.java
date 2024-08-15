package com.cofixer.mf.mfcontentapi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
@Table(name = "mf_discussion", uniqueConstraints = {
        @UniqueConstraint(name = "uk_state_id", columnNames = {"state_id"})
})
public class Discussion implements Serializable {

    @Serial
    private static final long serialVersionUID = -2035373917401072925L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("연결된 미션 상태 아이디")
    @Column(name = "state_id")
    Long stateId;

    @Comment("토론명")
    String name;

    @Comment("마지막 메세지")
    @Column(name = "latest_message")
    String latestMessage;

    @Comment("마지막 업데이트 시간")
    @Column(name = "latest_updated_at")
    Long latestUpdatedAt;

    public static Discussion of(String discussionName, Long stateId) {
        Discussion discussion = new Discussion();
        discussion.stateId = stateId;
        discussion.name = discussionName;
        discussion.latestMessage = "";
        discussion.latestUpdatedAt = 0L;

        return discussion;
    }

    public void renewLatest(String latestMessage, Long now) {
        this.latestMessage = latestMessage;
        this.latestUpdatedAt = now;
    }
}
