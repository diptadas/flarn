package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.User;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserRegistration {
    private final String username;
    private final String password;
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
