package edu.baylor.flarn.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    private JavaMailSender javaMailSender;

    @Value("${server.port}")
    private String port;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void sendVerificationEmail(String email, String confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("FLARN: Complete Registration!");
        // TODO: how to resolve host in production?
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:" + port + "/users/confirm?token=" + confirmationToken);

        log.info("Sending verification email: " + mailMessage.toString());
        sendEmail(mailMessage);
    }
}
