package edu.baylor.flarn.exceptions;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link AlreadyStaredException}.
 * class indicates user already stared the problem.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.CONFLICT)
@ExceptionMapping(statusCode = HttpStatus.CONFLICT,
        errorCode = "problem.already_stared")
public class AlreadyStaredException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor AlreadyStaredException.
     *
     * @param message exception message.
     */
    public AlreadyStaredException(final String message) {
        super(message);
    }
}
