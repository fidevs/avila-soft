package com.fidev.avilasoft.service;

import com.fidev.avilasoft.exception.ResponseException;
import com.fidev.avilasoft.views.NewAccountDTO;

public interface AccountService {
    NewAccountDTO recordNewAccount(NewAccountDTO newAccount) throws ResponseException;
}
