package com.cofixer.mf.mfcontentapi.repository.query;

import com.cofixer.mf.mfcontentapi.constant.FamilyMemberDirection;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberConnectRequest.Id;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;

import java.util.List;

public interface FamilyMemberConnectRequestQueryRepository {
    boolean hasRequested(FamilyMemberConnectRequest.Id id);

    List<FamilyMemberConnectRequest> getConnectRequests(FamilyMemberId familyMemberId, FamilyMemberDirection direction);

    FamilyMemberConnectRequest getConnectRequest(Id id);

    void deleteAllRequests(Long familyId, Long memberId);
}
