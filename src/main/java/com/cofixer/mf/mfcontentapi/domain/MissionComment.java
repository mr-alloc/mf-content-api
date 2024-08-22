package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_mission_comment", indexes = {
        @Index(name = "idx_discussion_id", columnList = "discussion_id")
})
public class MissionComment implements Serializable {

    @Serial
    private static final long serialVersionUID = 35005780494137386L;
    public static final Comparator<MissionComment> DEFAULT_SORT_CONDITION = Comparator.comparing(MissionComment::getCreatedAt);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("미션 상태 ID")
    @Column(name = "discussion_id", nullable = false)
    Long discussionId;

    @Comment("작성자 ID")
    @Column(name = "member_id", nullable = false)
    Long memberId;

    @Comment("댓글 내용")
    @Column(name = "content", nullable = false)
    String content;

    @Comment("작성일시")
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    public static MissionComment forCreate(Discussion discussion, AuthorizedMember authorizedMember, String comment) {
        MissionComment missionComment = new MissionComment();
        missionComment.discussionId = discussion.getId();
        missionComment.memberId = authorizedMember.getMemberId();
        missionComment.content = comment;
        missionComment.createdAt = TemporalUtil.getEpochSecond();

        return missionComment;
    }
}
