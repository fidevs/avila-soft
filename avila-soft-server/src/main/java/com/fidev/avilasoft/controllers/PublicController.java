package com.fidev.avilasoft.controllers;

import com.fidev.avilasoft.exception.ResponseException;
import com.fidev.avilasoft.service.AccountService;
import com.fidev.avilasoft.service.MailService;
import com.fidev.avilasoft.views.NewAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    private AccountService accountService;
    private MailService mailService;

    @Autowired
    public void setAccountService(AccountService accountService, MailService mailService) {
        this.accountService = accountService;
        this.mailService = mailService;
    }

    @PostMapping("/account")
    public String registerUser(@RequestBody NewAccountDTO dto) throws ResponseException {
        log.info("Request to save new account: {}", dto);
        NewAccountDTO account = accountService.recordNewAccount(dto); // Record account
        return mailService.sendActivationEmail(account.getEmail(), account.getUser(), account.getPswd()); // Send email
    }

    @PutMapping("/account")
    public String activateAccount(@RequestParam String token) throws ResponseException {
        log.info("Activating account by token: {}", token);
        return accountService.activateAccount(token);
    }

}
