package edu.baylor.flarn.exceptions;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.OK;

/**
 * The {@link EmailSendingException}.
 * class indicates failure to send confirmation email
 * @author Dipta Das
 */

@ResponseStatus(value = OK)
@ExceptionMapping(statusCode = OK, errorCode = "user.email_error")
public class EmailSendingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for EmailSendingException class.
     * @param cause the reason for the exception thrown
     */
    public EmailSendingException(final Throwable cause) {
        super(cause);
    }
}
