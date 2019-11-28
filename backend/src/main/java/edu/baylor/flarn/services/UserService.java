package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import edu.baylor.flarn.repositories.ActivityRepository;
import edu.baylor.flarn.repositories.CustomQueries;
import edu.baylor.flarn.repositories.UserRepository;
import edu.baylor.flarn.resources.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static edu.baylor.flarn.models.ReviewType.STAR;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CustomQueries customQueries;
    private final ActivityRepository activityRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService,
                       CustomQueries customQueries, ActivityRepository activityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.customQueries = customQueries;
        this.activityRepository = activityRepository;
    }

    public User findById(Long id) throws RecordNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RecordNotFoundException("can not fetch current user");
        }
        return user;
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
        user.setBiography(userDetails.getBiography());
        user.setAvatarLink(userDetails.getAvatarLink());
        user.setDpLink(userDetails.getDpLink());

        // TODO: update password more elegantly
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    // helper method, used in other service
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // user will follow other user specified by id
    public User follow(User user, Long id) throws RecordNotFoundException {
        User toBeFollowed = userRepository.findById(id).orElse(null);
        if (toBeFollowed == null) {
            throw new RecordNotFoundException("can not find user with id " + id);
        }

        user.subscribe(toBeFollowed);
        user = userRepository.save(user);

        // save the activity
        Activity activity = new Activity(user.getId(), user.getFullName());
        activity.setFollowedActivity(toBeFollowed.getId(), toBeFollowed.getFullName());
        activityRepository.save(activity);

        return user;
    }

    // user will unfollow other user specified by id
    public User unfollow(User user, Long id) throws RecordNotFoundException {
        User toBeUnFollowed = userRepository.findById(id).orElse(null);
        if (toBeUnFollowed == null) {
            throw new RecordNotFoundException("can not find user with id " + id);
        }

        user.unsubscribe(toBeUnFollowed);
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

    public List<User> getAllUsersOrderByPoints() {
        return userRepository.findAllByOrderByPointsDesc();
    }

    public List<User> getSubscriptionsOrderByPoints(User user) {
        List<Long> subscriptionIds = new ArrayList<>();
        user.getSubscriptions().forEach(e -> subscriptionIds.add(e.getId()));

        return userRepository.findByIdInOrderByPointsDesc(subscriptionIds);
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

    /**
     * AS far as I know hibernate does not have an implementation for onCascade set null yet.
     * So if we want to delete a user, we probably need to remove all his subscribers/subscriptions before proceeding.
     *
     * @param id
     * @return
     */
    public ResponseBody deleteUser(Long id) {
        try {
            //Remove all subscription associations
            ResponseBody responseBody = customQueries.deleteAssociations(id);

            if (responseBody.getStatus() == 200) {
                userRepository.deleteById(id);
                return new ResponseBody(200, "Successful");
            } else {
                return responseBody;
            }
        } catch (Exception e) {
            return new ResponseBody(500, e.getMessage());
        }

    }

    public List<User> getSubscribers(long Id) {
        return userRepository.findSubscribers(Id);
    }

    public List<User> getUserSubscriptions(long Id) {
        return userRepository.findUserSubscriptions(Id);
    }

    public List<User> searchUserByName(String name) {
        return userRepository.findByFullNameContainingIgnoreCase(name);
    }

    public List<Problem> getSolvedProblemsForUser(User user) {
        List<Problem> problems = new ArrayList<>();
        for (Session session : user.getParticipatedSessions()) {
            problems.add(session.getProblem());
        }
        return problems;
    }

    public List<Problem> getStaredProblemsForUser(User user) {
        List<Problem> problems = new ArrayList<>();
        for (Review review : user.getCreatedReviews()) {
            if (review.getReviewType().equals(STAR)) {
                problems.add(review.getProblem());
            }
        }
        return problems;
    }

    // TODO: use custom query
    public boolean hasAttempted(Long problemId, User user) {
        List<Problem> problems = new ArrayList<>();
        for (Session session : user.getParticipatedSessions()) {
            if (session.getProblem().getId().equals(problemId)) {
                return true;
            }
        }
        return false;
    }

    public User deactivateUser(User user) {
        user.setEnabled(false);
        return userRepository.save(user);
    }

    public List<Activity> activityForCurrentUser(User user) {
        return activityRepository.findByUserIdOrderByDateDesc(user.getId());
    }

    public List<Activity> activityOfSubscriptionsForCurrentUser(User user) {
        List<Long> subscriptionIds = new ArrayList<>();
        user.getSubscriptions().forEach(e -> subscriptionIds.add(e.getId()));

        return activityRepository.findByUserIdInOrderByDateDesc(subscriptionIds);
    }
}
