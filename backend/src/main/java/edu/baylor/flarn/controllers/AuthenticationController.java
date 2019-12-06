package edu.baylor.flarn.controllers;

import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Contact;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.*;
import edu.baylor.flarn.security.JwtTokenProvider;
import edu.baylor.flarn.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * The {@link AuthenticationController} class defines REST endpoints for user authentication.
 *
 * @author Dipta Das
 */

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();

            User user = (User) authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                    .getPrincipal();

            String token = jwtTokenProvider.createToken(username, userService.getUserByUsernameAll(username).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("userId", user.getId());
            model.put("enabled", user.isEnabled());
            model.put("userType", user.getUserType());
            return ok(model);
        } catch (AuthenticationException | RecordNotFoundException e) {
            log.info("Authentication failed " + e.getMessage());
            InvalidLogin invalidLogin = new InvalidLogin(data.getUsername(), data.getPassword(), e.getMessage());
            return new ResponseEntity<>(invalidLogin, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid UserRegistration data) {
        User user = userService.registerUser(data);
        userService.sendConfirmationCode(user);
        return user;
    }

    @GetMapping("/sendConfirmationCode")
    public boolean sendConfirmationCode(@RequestParam String username) throws RecordNotFoundException {
        User user = userService.getUserByUsernameAll(username);
        userService.sendConfirmationCode(user);
        return true;
    }

    @PostMapping("/confirm")
    public User confirmAccount(@RequestBody @Valid ConfirmUserRequest confirmUserRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        return userService.confirmUser(confirmUserRequest);
    }

    @PostMapping("/updatePassword")
    public User updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        return userService.updatePassword(updatePasswordRequest);
    }

    @PostMapping("/contact")
    public void contactSupport(@RequestBody @Valid Contact contact) {
        userService.contactSupport(contact);
    }

    @AllArgsConstructor
    @Data
    private static class InvalidLogin {
        private String username;
        private String password;
        private String error;
    }

}
