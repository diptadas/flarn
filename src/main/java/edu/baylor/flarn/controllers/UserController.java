package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.resources.UserUpdate;
import edu.baylor.flarn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/current")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ok(model);
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/usertype/{userType}")
    public List<User> getUsersByGroup(@PathVariable UserType userType) {
        List<User> users = userService.getUserByType(userType);
        return users;
    }


    @PutMapping("/usertype")
    public int updateUserType(@RequestBody UserUpdate userUpdate) {
        int status = userService.changeUserType(userUpdate);
        return status;
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable long id) {
        Optional<User> user = userService.getUser(id);
        return user;
    }

    @GetMapping("/subscribers/{id}")
    public List<User> getSubscribers(@PathVariable long id) {
        List<User> users = userService.getSubscribedUsers(id);
        return users;
    }

    @GetMapping("/subscription/{id}")
    public List<User> getSubscriptions(@PathVariable long id) {
        List<User> users = userService.getUserSubscriptions(id);
        return users;
    }

    // TODO: adding subscription repository


}
