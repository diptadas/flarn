package edu.baylor.flarn.controllers;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Activity;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.resources.UserRegistration;
import edu.baylor.flarn.resources.UserRoles;
import edu.baylor.flarn.resources.UserTypeUpdateRequest;
import edu.baylor.flarn.services.UserService;
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

/**
 * The {@link UserController} class defines REST endpoints for {@link User} model.
 *
 * @author Dipta Das
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        model.put("userId", user.getId());
        model.put("avatarLink", user.getAvatarLink());
        model.put("userType", user.getUserType());
        return ok(model);
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllActiveUsers();
    }

    @GetMapping("/type/{userType}")
    public List<User> getUsersByType(@PathVariable UserType userType) {
        return userService.getUserByType(userType);
    }

    // update current user
    @PostMapping("/current")
    public User updateCurrentUser(@RequestBody UserRegistration userDetails, @AuthenticationPrincipal User user) {
        return userService.updateUser(userDetails, user);
    }

    // don't delete the user, instead deactivate the account
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == #authUser.id")
    public ResponseBody deleteUser(@PathVariable("id") Long id, @AuthenticationPrincipal User authUser) throws RecordNotFoundException {
        deactivateCurrentUser(authUser);
        return new ResponseBody(200, "Successful");
    }

    @PostMapping("/type")
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
        return userService.getSubscribers(id);
    }

    @GetMapping("/{id}/subscriptions")
    public List<User> getSubscriptions(@PathVariable long id) {
        return userService.getUserSubscriptions(id);
    }

    // current user will follow user specified by {id}
    @PostMapping("/current/follow/{id}")
    public User follow(@PathVariable Long id, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.follow(user, id);
    }

    // current user will unfollow user specified by {id}
    @PostMapping("/current/unfollow/{id}")
    public User unfollow(@PathVariable Long id, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.unfollow(user, id);
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String name) {
        return userService.searchUserByName(name);
    }

    @GetMapping("/current/attemptedProblems")
    public List<Problem> attemptedProblems(@AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.getSolvedProblemsForUser(user);
    }

    @GetMapping("/current/staredProblems")
    public List<Problem> staredProblems(@AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.getStaredProblemsForUser(user);
    }

    // returns true if current user attempted the problem
    @GetMapping("/current/hasAttempted")
    public boolean hasAttemptedProblem(@RequestParam("problemId") Long problemId, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.hasAttempted(problemId, user);
    }

    @GetMapping("/current/deactivate")
    public User deactivateCurrentUser(@AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.deactivateUser(user);
    }

    // activities of current user
    @GetMapping("/current/activities")
    public List<Activity> activityForCurrentUser(@AuthenticationPrincipal User user) {
        return userService.activityForCurrentUser(user);
    }

    // activities filtered by subscriptions for current user
    // to be used in timeline
    @GetMapping("/current/subscriptionsActivities")
    public List<Activity> activityOfSubscriptionsForCurrentUser(@AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.activityOfSubscriptionsForCurrentUser(user);
    }

    @GetMapping("/orderByPoints")
    public List<User> getAllUsersOrderByPoints() {
        return userService.getAllUsersOrderByPoints();
    }

    @GetMapping("/current/subscriptionsOrderByPoints")
    public List<User> getSubscriptionsOrderByPoints(@AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return userService.getSubscriptionsOrderByPoints(user);
    }
}
