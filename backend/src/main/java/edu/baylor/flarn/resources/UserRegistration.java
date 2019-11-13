package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

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

    public User toUser(PasswordEncoder passwordEncoder) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        return new User(username, passwordEncoder.encode(password), fullName, phoneNumber, street, city, state, zip, roles);
    }
}
