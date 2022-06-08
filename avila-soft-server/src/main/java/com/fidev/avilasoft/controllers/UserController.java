package com.fidev.avilasoft.controllers;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Configuration configuration;

    @Autowired
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private JavaMailSender mailSender;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping
    public String greeting() throws IOException, TemplateException, MessagingException {
        log.info("Building email body...");
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("link", "https://www.baeldung.com/spring-email");
        model.put("user", "fidev");
        configuration.getTemplate("email.ftlh").process(model, stringWriter);

        String body = stringWriter.getBuffer().toString();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo("fidev.id@outlook.com");
        helper.setFrom("fidev.id@outlook.com", "AvilaSoft");
        helper.setText(body, true);
        helper.setSubject("SpringBoot Test 5");
        log.info("Sending email...");
        mailSender.send(message);
        log.info("Email sent successfully!");
        return "Hello World";
    }

}
