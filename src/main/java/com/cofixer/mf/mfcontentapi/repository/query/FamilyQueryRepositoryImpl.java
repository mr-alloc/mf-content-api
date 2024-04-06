package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.dto.res.FamilySummary;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cofixer.mf.mfcontentapi.domain.QFamily.family;
import static com.cofixer.mf.mfcontentapi.domain.QFamilyMember.familyMember;

@Repository
@RequiredArgsConstructor
public class FamilyQueryRepositoryImpl implements FamilyQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<FamilySummary> findOwnFamilies(Long mid) {
        return queryFactory.select(Projections.bean(FamilySummary.class,
                        familyMember.id.familyId.as("familyId"),
                        family.defaultColorHex.as("hexColor"),
                        family.logoImageUrl.as("imageUrl"),
                        family.name.as("familyName"),
                        family.description.as("familyDescription"),
                        familyMember.registeredAt.as("registeredAt")
                ))
                .from(familyMember)
                .innerJoin(family)
                .on(familyMember.id.familyId.eq(family.id))
                .where(familyMember.id.memberId.eq(mid))
                .fetch();
    }
}
