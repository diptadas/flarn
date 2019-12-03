package edu.baylor.flarn.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@link EmailSendingException}.
 * class indicates failure to authenticate the JWT token.
 *
 * @author Dipta Das
 */

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -761503632186596342L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
