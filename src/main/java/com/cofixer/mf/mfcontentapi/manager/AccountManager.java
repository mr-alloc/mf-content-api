package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.repository.AccountRepository;
import com.cofixer.mf.mfcontentapi.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AccountManager {

    /**
     * Required Bean
     */
    private final AccountRepository repository;

    @Transactional(propagation = Propagation.MANDATORY)
    public Account createAccount(Account newer) {
        return repository.save(newer);
    }

    public boolean isExistAccount(String email) {
        return repository.existsByEmail(email);
    }

    public Account getAccount(String email) {
        return repository.findByEmail(email)
                .orElse(null);
    }

    public Account getAccount(String email, Supplier<AccountException> exceptionSupplier) {
        return repository.findByEmail(email)
                .orElseThrow(exceptionSupplier);
    }
}
