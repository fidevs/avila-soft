package com.fidev.avilasoft.service;

import com.fidev.avilasoft.exception.ResponseException;

public interface MailService {
    String sendActivationEmail(String to, String user, String token) throws ResponseException;
}
