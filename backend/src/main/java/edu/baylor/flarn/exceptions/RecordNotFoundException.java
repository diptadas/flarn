package edu.baylor.flarn.exceptions;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * The {@link RecordNotFoundException} class indicates a specific data not found in the database.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@ExceptionMapping(statusCode = NOT_FOUND, errorCode = "user.not_found")
public class RecordNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message) {
        super(message);
    }
}
