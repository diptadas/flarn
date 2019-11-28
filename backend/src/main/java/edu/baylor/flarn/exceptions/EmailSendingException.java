package edu.baylor.flarn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link EmailSendingException} class indicates failure to send confirmation email.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmailSendingException(Throwable cause) {
        super(cause);
    }
}
