package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.User;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRegistration {
    @Email(message = "username.invalid")
    @NotNull(message = "username.required")
    @Column(unique = true)
    @Unique(message = "username.already_exists")
    private final String username;

    @NotNull(message = "password.required")
    private final String password;

    @NotNull(message = "fullName.required")
    private final String fullName;

    private final String phoneNumber;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private String biography;
    @URL
    private String avatarLink;
    @URL
    private String dpLink; // display picture

    // don't give option to specify userType
    // it will be set to LEARNER by default
    // ADMIN and MODERATOR first need to register as LEARNER, then promote
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullName, phoneNumber,
                street, city, state, zip, biography, avatarLink, dpLink, null);
    }
}
