package com.cofixer.mf.mfcontentapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_holiday")
public class Holiday implements Serializable {

    @Serial
    private static final long serialVersionUID = -1902233242956454038L;

    @Id
    @Column(name = "id")
    Long id;

    // 1: 양력, 2: 음력
    @Column(name = "type")
    Integer type;

    @Column(name = "month")
    Integer month;

    @Column(name = "day")
    Integer day;

    @Column(name = "name")
    String name;
}
