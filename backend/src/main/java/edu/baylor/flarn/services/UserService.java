package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.repositories.UserRepository;
import edu.baylor.flarn.resources.UserRegistration;
import edu.baylor.flarn.resources.UserRoles;
import edu.baylor.flarn.resources.UserTypeUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    public User getUser(long Id) {
        return userRepository.findById(Id).orElse(null);
    }

    /**
     * Method to promote/demote or change userType.
     *
     * @param
     * @param userTypeUpdateRequest
     */
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

    public List<User> getSubscribedUsers(long Id) {
        return userRepository.findSubscribedUsers(Id);
    }

    public List<User> getUserSubscriptions(long Id) {
        return userRepository.findUserSubscriptions(Id);
    }
}
