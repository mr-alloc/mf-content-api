package com.cofixer.mf.mfcontentapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Comment("공휴일 정보")
@Table(name = "mf_holiday")
public class Holiday implements Serializable {

    @Serial
    private static final long serialVersionUID = -1902233242956454038L;

    @Id
    @Column(name = "id")
    Long id;

    @Comment("1: 양력 / 2:음력")
    @Column(name = "type")
    Integer type;

    @Comment("월")
    @Column(name = "month")
    Integer month;

    @Comment("일")
    @Column(name = "day")
    Integer day;

    @Comment("공휴일명")
    @Column(name = "name")
    String name;
}
