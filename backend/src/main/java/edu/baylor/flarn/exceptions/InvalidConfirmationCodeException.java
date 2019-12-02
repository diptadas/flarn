package edu.baylor.flarn.exceptions;

import lombok.NoArgsConstructor;
import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The {@link InvalidConfirmationCodeException} class indicates confirmation code not matched.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@NoArgsConstructor
@ExceptionMapping(statusCode = BAD_REQUEST, errorCode = "user.confirmation_code_invalid")
public class InvalidConfirmationCodeException extends Exception {

    private static final long serialVersionUID = 1L;
}
