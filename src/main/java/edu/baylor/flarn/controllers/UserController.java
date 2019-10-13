package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.UserRegistration;
import edu.baylor.flarn.services.UserDetailsService;
import edu.baylor.flarn.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public User processRegistration(@RequestBody UserRegistration userRegistration) {
    return userService.registerUser(userRegistration);
  }
}
