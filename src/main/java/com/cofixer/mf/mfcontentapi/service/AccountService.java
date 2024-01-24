package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository repository;

    @Transactional
    public Account createAccount(CreateAccountReq req) {
        String encrypted = req.getPassword();
        Account newer = Account.forCreate(req.getEmail(), encrypted);
        //이메일 유효성확인

        //비밀번호 유효성 확인

        //이메일 중복 확인
        if (repository.existsByEmail(newer.getEmail())) {
            throw new AccountException();
        }

        //계정 생성
        return repository.save(newer);
    }
}
