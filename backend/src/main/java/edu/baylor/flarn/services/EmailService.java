package edu.baylor.flarn.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import edu.baylor.flarn.exceptions.EmailSendingException;
import edu.baylor.flarn.models.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Email service sends the verification email along with the confirmation code.
 * The confirmation code will be used to enable the account after registration.
 * It will also be used in case user forgets the password and wants to reset it.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Service
@Slf4j
public class EmailService {

    void sendVerificationEmail(String email, int confirmationCode) throws EmailSendingException {
        Email from = new Email("flarn@example.com"); // just a fake sender email
        Email to = new Email(email);

        String subject = "FLARN: Confirmation Code";
        Content content = new Content("text/plain", "Your confirmation code is " + confirmationCode);

        Mail mail = new Mail(from, subject, to, content);
        log.info("Sending verification email to " + email);

        try {
            sendEmail(mail);
        } catch (IOException e) {
            throw new EmailSendingException(e);
        }
    }

    void sendSupportEmail(String email, Contact contact) throws EmailSendingException {
        Email from = new Email("flarn@example.com"); // just a fake sender email
        Email to = new Email(email);

        String subject = "FLARN: Contact Support";

        StringBuilder builder = new StringBuilder();
        builder.append("Contact message from: ");
        builder.append(contact.getName());
        builder.append("\n\n");

        builder.append("With Email: ");
        builder.append(contact.getEmail());
        builder.append("\n\n");

        builder.append("Message: ");
        builder.append(contact.getMessage());
        builder.append("\n\n");

        Content content = new Content("text/plain", builder.toString());

        Mail mail = new Mail(from, subject, to, content);
        log.info("Sending support email to " + email);

        try {
            sendEmail(mail);
        } catch (IOException e) {
            throw new EmailSendingException(e);
        }
    }

    void replySupportEmail(Contact contact) throws EmailSendingException {
        Email from = new Email("flarn@example.com"); // just a fake sender email
        Email to = new Email(contact.getEmail());

        String subject = "FLARN: Contact Support";

        StringBuilder builder = new StringBuilder();
        builder.append("Hello ");
        builder.append(contact.getName());
        builder.append("\n\n");
        builder.append("Thank you for contacting Flarn team");
        builder.append("We will get back to you as soon as possible.");

        Content content = new Content("text/plain", builder.toString());

        Mail mail = new Mail(from, subject, to, content);
        log.info("Sending support email to " + contact.getEmail());

        try {
            sendEmail(mail);
        } catch (IOException e) {
            throw new EmailSendingException(e);
        }
    }

    private void sendEmail(Mail mail) throws IOException {
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
