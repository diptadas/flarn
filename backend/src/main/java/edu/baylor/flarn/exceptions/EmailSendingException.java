package edu.baylor.flarn.exceptions;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The {@link EmailSendingException} class indicates failure to send confirmation email.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@ExceptionMapping(statusCode = BAD_REQUEST, errorCode = "user.email_error")
public class EmailSendingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailSendingException(Throwable cause) {
        super(cause);
    }
}
