package edu.baylor.flarn.controllers;


import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserContoller {

    @Autowired
    UserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userType}")
    public List<User> getUsersByGroup(@PathVariable UserType userType) {
        List<User> users = userService.getUserByType(userType);
        return users;
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable long id) {
        Optional<User> user = userService.getUser(id);
        return user;
    }


}
