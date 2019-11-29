package edu.baylor.flarn.exceptions;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link DefaultCategoryModificationException} class indicates a default category (other) is immutable.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN)
@ExceptionMapping(statusCode = HttpStatus.FORBIDDEN, errorCode = "category.cannot_modify")
public class DefaultCategoryModificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public DefaultCategoryModificationException() {
        super("Default category (other) is immutable");
    }
}
