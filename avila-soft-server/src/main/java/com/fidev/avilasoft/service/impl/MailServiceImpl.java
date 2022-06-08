package com.fidev.avilasoft.service.impl;

import com.fidev.avilasoft.exception.ResponseException;
import com.fidev.avilasoft.service.MailService;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    public static final String FROM_EMAIL = "fidev.id@outlook.com";
    public static final String FROM_NAME = "AvilaSoft";
    private final Configuration configuration;
    private final JavaMailSender mailSender;

    public MailServiceImpl(Configuration configuration, JavaMailSender mailSender) {
        this.configuration = configuration;
        this.mailSender = mailSender;
    }

    @Override
    public String sendActivationEmail(String to, String user, String token) throws ResponseException {
        log.info("Building email body...");
        // Objects to make email body
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        // Object to map in email template
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("link", "http://localhost:3000/activate-account?token="+token);
        model.put("user", user);

        try {
            configuration.getTemplate("email.ftlh").process(model, stringWriter); // Obtain and map template
            String body = stringWriter.getBuffer().toString(); // Get email body as text
            // Configure email details
            helper.setTo(to);
            helper.setFrom(FROM_EMAIL, FROM_NAME);
            helper.setText(body, true);
            helper.setSubject("Activación de cuenta");
        } catch (Exception e) {
            log.error("Exception sending email: {}", e.getMessage());
            e.printStackTrace();
        }
        log.info("Sending email...");
        mailSender.send(message); // Send email
        log.info("Email sent successfully!");
        return to;
    }
}
