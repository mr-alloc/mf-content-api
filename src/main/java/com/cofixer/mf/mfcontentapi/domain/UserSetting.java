package com.cofixer.mf.mfcontentapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@DynamicUpdate
@Comment("유저 설정 정보")
@Table(name = "mf_user_setting")
public class UserSetting {

    @Id
    @Comment("멤버 아이디")
    @Column(name = "mid")
    Long memberId;

    @Comment("메인 패밀리")
    @Column(name = "main_family", nullable = false)
    Long mainFamilyId;

    public static UserSetting forBeginner(Long memberId) {
        UserSetting userSetting = new UserSetting();
        userSetting.memberId = memberId;
        userSetting.mainFamilyId = 0L;

        return userSetting;
    }

    public void changeMainFamily(Long familyId) {
        this.mainFamilyId = familyId;
    }
}
