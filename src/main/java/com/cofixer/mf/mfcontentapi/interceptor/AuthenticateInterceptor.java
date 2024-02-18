package com.cofixer.mf.mfcontentapi.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.dto.AuthorizedInfo;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.CredentialManager;
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


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticateInterceptor implements HandlerInterceptor {

    private final CredentialManager credentialManager;
    private final MemberManager memberManager;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractToken(request.getHeader(AUTHORIZATION_HEADER));
        log.info("[REQUEST] {} {}", request.getMethod(), request.getRequestURI());
        if (token != null) {
            DecodedJWT decoded = JwtUtil.decode(token);
            boolean isPassed = isNotExpired(decoded) && isSyncedToken(decoded, token) && isCacheAuthorization(request, decoded);
            if (!isPassed) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

            return isPassed;
        }
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return false;
    }

    private boolean isCacheAuthorization(HttpServletRequest request, DecodedJWT decoded) {
        long accountId = Long.parseLong(decoded.getIssuer());
        Member member = memberManager.getMayMemberByAccountId(accountId)
                .orElseThrow(() -> new MemberException(DeclaredMemberResult.NOT_FOUND_MEMBER, String.valueOf(accountId)));

        AuthorizedService.setInfo(request, new AuthorizedInfo(accountId, member.getId(), member.getMemberRole()));
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
