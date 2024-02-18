package com.cofixer.mf.mfcontentapi.service;

import com.cofixer.mf.mfcontentapi.constant.DeclaredAccountResult;
import com.cofixer.mf.mfcontentapi.dto.req.CreateAccountReq;
import com.cofixer.mf.mfcontentapi.exception.AccountException;
import com.cofixer.mf.mfcontentapi.manager.AccountManager;
import com.cofixer.mf.mfcontentapi.manager.MemberManager;
import com.cofixer.mf.mfcontentapi.validator.AccountValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountManager accountManager;

    @Mock
    AccountValidator accountValidator;

    @Mock
    MemberManager memberManager;


    public static CreateAccountReq getCreateAccountReq(String email, String password) {
        CreateAccountReq req = new CreateAccountReq();
        req.setEmail(email);
        req.setPassword(password);
        return req;
    }

    @Test
    @DisplayName("[가입실패] 이메일 중복")
    void createAccount1() throws Throwable {
        /* Given */
        CreateAccountReq req = getCreateAccountReq("testid", "testpassword");

        /* When */
        when(accountManager.isExistAccount(req.getEmail()))
                .thenReturn(true);
        AccountException thrown = assertThrows(AccountException.class, () -> accountService.createAccount(req));

        /* Then */
        assertEquals(DeclaredAccountResult.DUPLICATED_EMAIL.getCode(), thrown.getCode());
    }
}