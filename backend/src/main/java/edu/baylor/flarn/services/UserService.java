package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.repositories.UserRepository;
import edu.baylor.flarn.resources.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User registerUser(UserRegistration userRegistration) {
        return userRepository.save(userRegistration.toUser(passwordEncoder));
    }

    // user is current user
    // user details represents updated fields
    public User updateUser(UserRegistration userDetails, User user) {
        // copy the fields that are allowed to update
        // username/email and associations are not allowed to update
        user.setFullName(userDetails.getFullName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setState(userDetails.getState());
        user.setCity(userDetails.getCity());
        user.setStreet(userDetails.getStreet());
        user.setZip(userDetails.getZip());

        // TODO: update password more elegantly
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    // associate a confirmation code and send it to email
    public void sendConfirmationCode(User user) {
        int code = new Random().nextInt(9000) + 1000; // 4 digit code
        user.setConfirmationCode(code);
        userRepository.save(user);

        emailService.sendVerificationEmail(user.getUsername(), code); // username is email
    }

    // enable user after email verification
    public User confirmUser(ConfirmUserRequest confirmUserRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        User user = getUserByUsername(confirmUserRequest.getUsername());

        if (user.getConfirmationCode() == null || !user.getConfirmationCode().equals(confirmUserRequest.getConfirmationCode())) {
            throw new InvalidConfirmationCodeException();
        }

        user.setConfirmationCode(null); // reset confirmation code
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User updatePassword(UpdatePasswordRequest updatePasswordRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        User user = getUserByUsername(updatePasswordRequest.getUsername());

        if (user.getConfirmationCode() == null || !user.getConfirmationCode().equals(updatePasswordRequest.getConfirmationCode())) {
            throw new InvalidConfirmationCodeException();
        }

        user.setConfirmationCode(null); // reset confirmation code
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    public User getUser(long Id) {
        return userRepository.findById(Id).orElse(null);
    }

    public User getUserByUsername(String username) throws RecordNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new RecordNotFoundException("User not found with username " + username);
        }
        return user;
    }

    public User changeUserType(UserTypeUpdateRequest userTypeUpdateRequest) throws RecordNotFoundException {
        User user = userRepository.findById(userTypeUpdateRequest.getId()).orElse(null);

        if (user == null) {
            throw new RecordNotFoundException("user not found with id " + userTypeUpdateRequest.getId());
        }

        // update only if userType changed
        if (!user.getUserType().equals(userTypeUpdateRequest.getUserType())) {
            user.setUserType(userTypeUpdateRequest.getUserType());
            // also update the roles
            user.setRoles(UserRoles.rolesForUserType(userTypeUpdateRequest.getUserType()));
            userRepository.save(user);
        }
        return user;
    }

    public ResponseBody deleteUser(Long id ){
        try {
            userRepository.deleteById(id);
            return  new ResponseBody(200,"Successful");
        }
        catch (Exception e){
            return  new ResponseBody(500,e.getMessage());
        }
    }

    public List<User> getSubscribedUsers(long Id) {
        return userRepository.findSubscribedUsers(Id);
    }

    public List<User> getUserSubscriptions(long Id) {
        return userRepository.findUserSubscriptions(Id);
    }
}
