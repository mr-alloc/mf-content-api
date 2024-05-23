package com.cofixer.mf.mfcontentapi.domain;

import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "mf_place")
public class Place implements Serializable {

    @Serial
    private static final long serialVersionUID = 9177134091249350663L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "register_id", nullable = false)
    Long register;

    @Column(nullable = false)
    String name;

    String address;

    String phone;

    String description;

    @Column(name = "image_url")
    String imageUrl;

    Double latitude;

    Double longitude;

    @Column(name = "created_at", nullable = false)
    Long createdAt;

    public static Place forCreate(Long register, String name) {
        Place newPlace = new Place();

        newPlace.register = register;
        newPlace.name = name;
        newPlace.createdAt = TemporalUtil.getEpochSecond();

        return newPlace;
    }

}
