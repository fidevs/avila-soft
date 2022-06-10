package com.fidev.avilasoft.service.impl;

import com.fidev.avilasoft.entities.*;
import com.fidev.avilasoft.exception.ResponseException;
import com.fidev.avilasoft.repositories.RoleRepository;
import com.fidev.avilasoft.repositories.UserRepository;
import com.fidev.avilasoft.repositories.UsrTransactionRepository;
import com.fidev.avilasoft.service.AccountService;
import com.fidev.avilasoft.util.AppConst;
import com.fidev.avilasoft.views.NewAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Value("${default.user-role}")
    private String DEFAULT_ROLE_ID;

    public static final long ACTIVATE_TOKEN_VALIDITY = 1800000; // 30 MINUTES

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UsrTransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
            UsrTransactionRepository transactionRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public NewAccountDTO recordNewAccount(NewAccountDTO newAccount) throws ResponseException {
        if (userRepository.existsByEmail(newAccount.getEmail())) { // Verify that email not exists
            log.warn("User with email: {} already registered", newAccount.getEmail());
            throw new ResponseException(AppConst.EMAIL_EXISTS_CODE, AppConst.EMAIL_EXISTS_MSG);
        }

        if (userRepository.existsByUsername(newAccount.getUser())) { // Verify that user not exists
            log.warn("User with username: {} already registered", newAccount.getUser());
            throw new ResponseException(AppConst.USER_EXISTS_CODE, AppConst.USER_EXISTS_MSG);
        }

        Optional<Role> optRole = roleRepository.findById(DEFAULT_ROLE_ID); // Search default role for new accounts
        if (optRole.isEmpty()) { // Not found default role
            log.error("Not found default role by id: {}", DEFAULT_ROLE_ID);
            throw new ResponseException(AppConst.DEFAULT_ROLE_404_CODE, AppConst.DEFAULT_ROLE_404_MSG);
        }
        Role role = optRole.get();


        // Generate new account
        User user = new User(newAccount.getEmail(), newAccount.getUser(), newAccount.getPswd());
        user.setRole(role); // Configure role
        role.getUsers().add(user);
        UserConfig config = new UserConfig(); // Generate account configs
        config.setUser(user);
        user.setConfig(config);
        UsrTransaction activateTrans = new UsrTransaction(UserTransType.ACTIVATE_ACCOUNT, ACTIVATE_TOKEN_VALIDITY);
        activateTrans.setUser(user); // Generate activation token
        user.getTransactions().add(activateTrans);

        userRepository.save(user);
        log.info("Account saved successfully!");

        newAccount.setPswd(activateTrans.getToken()); // Set token to response
        return newAccount;
    }

    @Override
    public String activateAccount(String token) throws ResponseException {
        Optional<UsrTransaction> optTrans = transactionRepository.findByToken(token);
        if (optTrans.isEmpty()) { // Not found token
            log.error("Not found transaction by token: {}", token);
            throw new ResponseException(AppConst.NOT_FOUND, AppConst.NOT_FOUND_TOKEN);
        }
        UsrTransaction trans = optTrans.get();

        if (trans.isRedeemed() || new Date().getTime() > trans.getLimitDate().getTime()) {
            log.error("Token {} isn't valid", token);
            throw new ResponseException(AppConst.CONFLICT, AppConst.INVALID_TOKEN);
        }

        trans.setRedeemed(true); // Invalidate token
        trans.getUser().setActive(true); // Activate user account
        trans.getUser().getConfig().setLastUpdate(new Date().getTime()); // Update user version
        this.transactionRepository.save(trans);

        return trans.getUser().getUsername();
    }
}
