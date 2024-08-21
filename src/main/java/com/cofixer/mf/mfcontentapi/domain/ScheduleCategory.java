package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.constant.DeclaredValidateResult;
import com.cofixer.mf.mfcontentapi.constant.SelectorType;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.exception.ValidateException;
import com.cofixer.mf.mfcontentapi.util.StringUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "mf_schedule_category", indexes = {
        @Index(name = "idx_selector", columnList = "selector_type, selector")
})
public class ScheduleCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = 5353967286447593109L;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_COLOR_LENGTH = 10;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("선택자 타입 0: 유저, 1: 패밀리")
    @Column(name = "selector_type", nullable = false)
    Integer selectorType;

    @Comment("선택자 타입에 따른 ID")
    @Column(name = "selector", nullable = false)
    Long selector;

    @Comment("카테고리명")
    @Column(nullable = false, length = MAX_NAME_LENGTH)
    String name;

    @Comment("카테고리 색상")
    @Column(nullable = false, length = MAX_COLOR_LENGTH)
    String color;

    @Comment("카테고리 설명")
    String description;

    @Comment("순서")
    @Column(name = "order", nullable = false)
    Integer order;

    public static ScheduleCategory of(AuthorizedMember authorizedMember, String name, String color, String description) {
        validate(name, color);
        ScheduleCategory category = new ScheduleCategory();

        boolean isFamily = authorizedMember.forFamilyMember();
        category.selectorType = isFamily
                ? SelectorType.FAMILY.getValue()
                : SelectorType.USER.getValue();

        category.selector = isFamily
                ? authorizedMember.getFamilyId()
                : authorizedMember.getMemberId();

        category.name = name;
        category.color = color;
        category.description = description;
        category.order = 0;

        return category;
    }

    private static void validate(String name, String color) {
        StringUtil.checkRange(name, MAX_NAME_LENGTH);
        StringUtil.checkRange(color, MAX_COLOR_LENGTH);
    }
}
