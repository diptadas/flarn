package edu.baylor.flarn.services;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.UserRepository;
import edu.baylor.flarn.resources.UserRegistration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public User registerUser(UserRegistration userRegistration) {
    return userRepository.save(userRegistration.toUser(passwordEncoder));
  }
}
