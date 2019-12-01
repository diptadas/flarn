package edu.baylor.flarn.jms;

import com.sendgrid.helpers.mail.Mail;
import edu.baylor.flarn.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Receiver {
    private final EmailService emailService;

    public Receiver(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "flarnJMSDestination", containerFactory = "flarnJMSFactory")
    public void receiveMessage(Mail mail) {
        log.info("Received jms message: " + mail);

        // send the email
        try {
            emailService.sendEmail(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
