package edu.baylor.flarn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link AlreadyStaredException} class indicates a specific data not found in the database.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyStaredException extends Exception {

    private static final long serialVersionUID = 1L;

    public AlreadyStaredException(String message) {
        super(message);
    }
}
