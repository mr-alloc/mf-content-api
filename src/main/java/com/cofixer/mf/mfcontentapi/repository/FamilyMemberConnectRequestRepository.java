package com.cofixer.mf.mfcontentapi.repository;

import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;
import com.cofixer.mf.mfcontentapi.repository.query.FamilyMemberConnectRequestQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest.Id;

@Repository
public interface FamilyMemberConnectRequestRepository extends JpaRepository<FamilyMemberConnectRequest, Id>, FamilyMemberConnectRequestQueryRepository {

}
