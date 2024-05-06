package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import com.cofixer.mf.mfcontentapi.dto.req.CreateFamilyReq;
import com.cofixer.mf.mfcontentapi.util.EncryptUtil;
import com.cofixer.mf.mfcontentapi.util.RandomUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
@Table(name = "mf_family", uniqueConstraints = {
        @UniqueConstraint(name = "uk_invite_code", columnNames = {"invite_code"})
})
public class Family implements Serializable {
    @Serial
    private static final long serialVersionUID = -4878795371659623625L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "creator_id", nullable = false)
    Long creatorId;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "logo_image_url")
    String logoImageUrl;

    @Column(name = "default_color_hex")
    String defaultColorHex;

    @Column(name = "invite_code")
    String inviteCode;

    @Column(name = "created_at", nullable = false)
    Long createdAt;


    public static Family forCreate(CreateFamilyReq req, Long mid) {
        Family newer = new Family();
        newer.creatorId = mid;
        newer.name = req.getFamilyName();
        newer.description = req.getFamilyDescription();
        newer.defaultColorHex = RandomUtil.generateRandomHexColor();
        newer.createdAt = AppContext.APP_CLOCK.instant().getEpochSecond();

        return newer;
    }

    public void initInviteCode() {
        this.inviteCode = EncryptUtil.encrypt(String.valueOf(this.id), EncryptAlgorithm.MD5);
    }
}
