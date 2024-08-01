package com.cofixer.mf.mfcontentapi.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cofixer.mf.mfcontentapi.AppContext;
import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.constant.DeclaredMemberResult;
import com.cofixer.mf.mfcontentapi.constant.DeviceType;
import com.cofixer.mf.mfcontentapi.constant.EncryptAlgorithm;
import com.cofixer.mf.mfcontentapi.domain.AccessCredential;
import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.domain.Member;
import com.cofixer.mf.mfcontentapi.domain.PreflightTester;
import com.cofixer.mf.mfcontentapi.dto.req.ConfirmAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.dto.req.RefreshTokenReq;
import com.cofixer.mf.mfcontentapi.dto.req.VerifyAccountReq;
import com.cofixer.mf.mfcontentapi.dto.res.AccountInfoRes;
import com.cofixer.mf.mfcontentapi.dto.res.VerifiedAccountRes;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.exception.MemberException;
import com.cofixer.mf.mfcontentapi.manager.AccountManager;
import com.cofixer.mf.mfcontentapi.manager.CredentialManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.util.EncryptUtil;
import com.cofixer.mf.mfcontentapi.util.JwtUtil;
import com.cofixer.mf.mfcontentapi.util.TemporalUtil;
import com.cofixer.mf.mfcontentapi.validator.CommonValidator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final CommonValidator commonValidator;
    private final AccountManager accountManager;
    private final MemberManager memberManager;
    private final CredentialManager credentialManager;
    private final EntityPrinter printer;
    private final ExpandableEntitySetting es;

    @Transactional
    public Account createAccount(CreateAccountReq req) {
        //입력값 검증
        commonValidator.validateAccount(req.getEmail(), req.getPassword());

        //이메일 중복 확인
        if (accountManager.isExistAccount(req.getEmail())) {
            throw new AccountException(DeclaredAccountResult.DUPLICATED_EMAIL);
        }

        //테스터 여부 확인
        PreflightTester tester = accountManager.checkPreflightTester(req.getEmail());

        //계정 생성
        Account saved = accountManager.createAccount(
                Account.forCreate(
                        req.getEmail(),
                        EncryptUtil.encrypt(req.getPassword(), EncryptAlgorithm.SHA256)
                )
        );
        memberManager.createMember(saved.getId());
        accountManager.markTesterJoin(tester);
        return saved;
    }

    @Transactional
    public VerifiedAccountRes verifyAccount(VerifyAccountReq req) {
        //입력값 검증
        commonValidator.validateAccount(req.getEmail(), req.getPassword());

        //계정 조회
        Account found = accountManager.getExistedAccount(req.getEmail(),
                () -> new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT));

        //이용정지 확인
        memberManager.getMayMemberByAccountId(found.getId())
                .filter(Member::isBlocked)
                .ifPresent(member -> {
                    throw new MemberException(DeclaredMemberResult.BLOCKED_MEMBER);
                });
        String encrypted = EncryptUtil.encrypt(req.getPassword(), EncryptAlgorithm.SHA256);
        //비밀번호 확인
        if (!found.getPassword().equals(encrypted)) {
            throw new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT);
        }

        Instant instant = AppContext.APP_CLOCK.instant();
        log.info(printer.drawEntity(found, es.getConfig()));

        //TODO 토큰생성시 deviceCode를 추가할지 검토
        String accessToken = JwtUtil.createToken(found.getId(), instant, AppContext.ACCESS_TOKEN_EXPIRE_SECOND);
        String refreshToken = JwtUtil.createToken(found.getId(), instant, AppContext.REFRESH_TOKEN_EXPIRE_SECOND);

        //로그인 일시 수정
        found.renewSignedInAt(instant);

        //토큰 등록
        credentialManager.registerCredential(found.getId(), req.getDeviceCode(), accessToken, refreshToken);

        return new VerifiedAccountRes(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public AccountInfoRes getAccountInfo(Long aid) {
        Account account = accountManager.getExistedAccount(aid);
        return new AccountInfoRes(account.getEmail(), account.getPhone(), account.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public void confirmAccount(ConfirmAccountReq req) {
        //생성규칙 확인
        commonValidator.validateEmail(req.email());

        //계정 조회
        if (accountManager.isExistAccount(req.email())) {
            throw new AccountException(DeclaredAccountResult.DUPLICATED_EMAIL);
        }
        //테스터 여부 확인
        accountManager.checkPreflightTester(req.email());
    }

    @Transactional
    public String createAccessToken(RefreshTokenReq request) {
        DecodedJWT decodedRefreshToken = JwtUtil.decode(request.refreshToken());
        long refreshTokenExpiry = decodedRefreshToken.getExpiresAtAsInstant().getEpochSecond();
        if (TemporalUtil.getEpochSecond() > refreshTokenExpiry) {
            throw new AccountException(DeclaredAccountResult.EXPIRED_REFRESH_TOKEN);
        }
        DecodedJWT decodedAccessToken = JwtUtil.decodeWithoutExpiry(request.accessToken());
        long refreshTokenIssuer = Long.parseLong(decodedRefreshToken.getIssuer());
        long accessTokenIssuer = Long.parseLong(decodedAccessToken.getIssuer());

        if (refreshTokenIssuer != accessTokenIssuer) {
            throw new AccountException(DeclaredAccountResult.INVALID_ACCESS_TOKEN);
        }

        DeviceType deviceType = DeviceType.fromCode(request.deviceCode());
        AccessCredential credential = credentialManager.getCredential(deviceType, request.refreshToken());
        if (credential.isNotEqualsAccessToken(request.accessToken())) {
            throw new AccountException(DeclaredAccountResult.INVALID_ACCESS_TOKEN);
        }

        String newAccessToken = JwtUtil.createToken(refreshTokenIssuer, AppContext.APP_CLOCK.instant(), AppContext.ACCESS_TOKEN_EXPIRE_SECOND);
        credentialManager.updateAccessToken(credential, newAccessToken);

        return newAccessToken;
    }
}
