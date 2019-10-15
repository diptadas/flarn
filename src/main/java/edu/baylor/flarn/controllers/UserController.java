package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.resources.UserTypeUpdateRequest;
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
        return userService.getUserByType(userType);
    }


    @PutMapping("/usertype")
    public int updateUserType(@RequestBody UserTypeUpdateRequest userTypeUpdateRequest) {
        return userService.changeUserType(userTypeUpdateRequest);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/subscribers/{id}")
    public List<User> getSubscribers(@PathVariable long id) {
        return userService.getSubscribedUsers(id);
    }

    @GetMapping("/subscription/{id}")
    public List<User> getSubscriptions(@PathVariable long id) {
        return userService.getUserSubscriptions(id);
    }

    // TODO: adding subscription repository


}
