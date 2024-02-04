package com.cofixer.mf.mfcontentapi.manager;

import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.domain.Account;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.repository.AccountRepository;
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

    public Account getExistedAccount(String email) {
        return repository.findByEmail(email)
                .orElse(null);
    }

    public Account getExistedAccount(String email, Supplier<AccountException> exceptionSupplier) {
        return repository.findByEmail(email)
                .orElseThrow(exceptionSupplier);
    }

    public Account getExistedAccount(Long accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new AccountException(DeclaredAccountResult.NOT_FOUND_ACCOUNT));
    }
}
