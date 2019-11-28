package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.EmailSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) throws EmailSendingException {
        try {
            javaMailSender.send(email);
        } catch (Exception e) {
            throw new EmailSendingException(e);
        }
    }

    public void sendVerificationEmail(String email, int confirmationCode) throws EmailSendingException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("FLARN: Confirmation Code");
        mailMessage.setText("Your confirmation code is " + confirmationCode);

        log.info("Sending verification email: " + mailMessage.toString());
        sendEmail(mailMessage);
    }
}
