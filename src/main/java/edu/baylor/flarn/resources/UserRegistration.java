package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserRegistration {
  private final String username;
  private final String password;
  private final String fullname;
  private final String phoneNumber;
  private final String street;
  private final String city;
  private final String state;
  private final String zip;

  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(username, passwordEncoder.encode(password), fullname, phoneNumber, street, city, state, zip);
  }
}
