package edu.baylor.flarn.resources;

import edu.baylor.flarn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link UserUniqueValidator} class enforces an unique email validation logic for the {@link edu.baylor.flarn.models.User} model.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

public class UserUniqueValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(Unique unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userService != null) {
            return !userService.exists(email);
        }
        return true;
    }
}
