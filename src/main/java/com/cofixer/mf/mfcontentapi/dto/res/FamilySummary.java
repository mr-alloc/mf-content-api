package com.cofixer.mf.mfcontentapi.dto.res;

import lombok.Data;

@Data
public class FamilySummary {

    Long familyId;
    String hexColor;
    String imageUrl;
    String familyName;
    String familyDescription;
    Long registeredAt;
}
