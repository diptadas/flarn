package edu.baylor.flarn.controllers;

import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.resources.*;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
    public ResponseEntity currentUser(@AuthenticationPrincipal User user) {
        assert (user != null);
        Map<Object, Object> model = new HashMap<>();
        model.put("username", user.getUsername());
        model.put("roles", user.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        model.put("fullName", user.getFullName());
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

    @PostMapping("/")
    // update current user
    public User updateUser(@RequestBody UserRegistration userDetails, @AuthenticationPrincipal User user) {
        return userService.updateUser(userDetails, user);
    }

    @GetMapping("/sendConfirmationCode")
    public void sendConfirmationCode(@RequestParam String username) throws RecordNotFoundException {
        User user = userService.getUserByUsername(username);
        userService.sendConfirmationCode(user);
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or #user.username == authentication.name")
    //Authentication principal not needed here
    public ResponseBody deleteUser(@RequestBody User user, @AuthenticationPrincipal User authUser) {
        return userService.deleteUser(user);
    }

    @PostMapping("/confirm")
    public User confirmAccount(@RequestBody ConfirmUserRequest confirmUserRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        return userService.confirmUser(confirmUserRequest);
    }

    @PostMapping("/updatePassword")
    public User updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        return userService.updatePassword(updatePasswordRequest);
    }

    @PostMapping("/usertype")
    @RolesAllowed(UserRoles.roleAdmin)
    public User updateUserType(@RequestBody UserTypeUpdateRequest userTypeUpdateRequest) throws RecordNotFoundException {
        return userService.changeUserType(userTypeUpdateRequest);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/subscribers")
    public List<User> getSubscribers(@PathVariable long id) {
        return userService.getSubscribedUsers(id);
    }

    @GetMapping("/{id}/subscriptions")
    public List<User> getSubscriptions(@PathVariable long id) {
        return userService.getUserSubscriptions(id);
    }
}
