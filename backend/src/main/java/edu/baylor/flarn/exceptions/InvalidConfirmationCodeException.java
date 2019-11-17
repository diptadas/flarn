package edu.baylor.flarn.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link InvalidConfirmationCodeException} class indicates confirmation code not matched.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class InvalidConfirmationCodeException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidConfirmationCodeException(String message) {
        super(message);
    }
}
