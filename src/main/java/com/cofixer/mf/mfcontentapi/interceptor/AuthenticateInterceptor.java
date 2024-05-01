package com.cofixer.mf.mfcontentapi.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.UserProtocol;
import com.cofixer.mf.mfcontentapi.domain.FamilyMember;
import com.cofixer.mf.mfcontentapi.domain.FamilyMemberId;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedMember;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.CredentialManager;
import com.cofixer.mf.mfcontentapi.manager.FamilyManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.service.AuthorizedService;
import com.cofixer.mf.mfcontentapi.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticateInterceptor implements HandlerInterceptor {

    private final CredentialManager credentialManager;
    private final MemberManager memberManager;
    private final FamilyManager familyManager;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = extractToken(request.getHeader(AUTHORIZATION_HEADER));
            log.info("[REQUEST] {} {}, family: {}", request.getMethod(), request.getRequestURI(), request.getHeader(UserProtocol.HEADER_FAMILY_ID));
            if (token != null) {
                DecodedJWT decoded = JwtUtil.decode(token);
                boolean isPassed = isNotExpired(decoded) && isCacheAuthorization(request, decoded);
                if (!isPassed) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
                return isPassed;
            }
        } catch (SignatureVerificationException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception ex) {
            log.error("Failed to authenticate", ex);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return false;
    }

    private boolean isCacheAuthorization(HttpServletRequest request, DecodedJWT decoded) {
        long accountId = Long.parseLong(decoded.getIssuer());
        Member member = memberManager.getMayMemberByAccountId(accountId)
                .orElseThrow(() -> new MemberException(DeclaredMemberResult.NOT_FOUND_MEMBER, String.valueOf(accountId)));

        Optional<FamilyMember> mayFamilyMember = Optional.ofNullable(request.getHeader(UserProtocol.HEADER_FAMILY_ID))
                .map(Long::parseLong)
                .filter(familyId -> !UserProtocol.NOT_SELECTED_FAMILY_ID.equals(familyId))
                .map(familyId -> familyManager.getFamilyMember(FamilyMemberId.of(familyId, member.getId())));

        AuthorizedService.setInfo(request, AuthorizedMember.of(accountId, member, mayFamilyMember));
        return true;
    }

    private boolean isNotExpired(DecodedJWT decoded) {
        return decoded.getExpiresAtAsInstant().isAfter(AppContext.APP_CLOCK.instant());
    }

    private boolean isSyncedToken(DecodedJWT decoded, String token) {
        return credentialManager.getMayCredential(Long.parseLong(decoded.getIssuer()))
                .map(authorized -> authorized.getCredential().equals(token))
                .orElse(false);
    }

    private String extractToken(String header) {
        if (!StringUtils.hasLength(header)) {
            return null;
        }
        String[] value = header.split(" ");
        return value.length == 2 ? value[1] : null;
    }
}
