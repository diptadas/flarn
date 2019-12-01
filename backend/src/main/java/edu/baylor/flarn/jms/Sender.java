package edu.baylor.flarn.jms;

import com.sendgrid.helpers.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {
    private final JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(Mail mail) {
        log.info("Sending jms message: " + mail);
        jmsTemplate.convertAndSend("flarnJMSDestination", mail);
    }
}
