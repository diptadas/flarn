package edu.baylor.flarn.services;

import edu.baylor.flarn.adapter.UserAdapter;
import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.jms.Sender;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.models.Activity;
import edu.baylor.flarn.models.Contact;
import edu.baylor.flarn.repositories.ContactRepository;
import edu.baylor.flarn.repositories.UserRepository;
import edu.baylor.flarn.resources.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static edu.baylor.flarn.models.ReviewType.STAR;

/**
 * User service provides CRUD operations on the User model.
 * However, it doesn't allow the delete operation, instead it disables the user account.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ActivityService activityService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       EmailService emailService, ActivityService activityService, Sender jmsSender, ContactRepository contactRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.emailService = emailService;
        this.activityService = activityService;
    }

    public User findById(Long id) throws RecordNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RecordNotFoundException("can not fetch current user");
        }
        return user;
    }

    public boolean exists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
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

        user = userRepository.save(user);

        // save the activity
        activityService.saveUpdatedProfileActivity(user);

        return user;
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
        activityService.saveFollowedActivity(user, toBeFollowed);

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

        // prepare the email
        // send mail
        emailService.sendVerificationEmail(user.getUsername(), code); // username is email
    }

    // enable user after email verification
    public User confirmUser(ConfirmUserRequest confirmUserRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        //can filter only inactive users
        User user = getUserByUsernameAll(confirmUserRequest.getUsername());

        if (user.getConfirmationCode() == null || !user.getConfirmationCode().equals(confirmUserRequest.getConfirmationCode())) {
            throw new InvalidConfirmationCodeException();
        }

        user.setConfirmationCode(null); // reset confirmation code
        user.setEnabled(true);
        user = userRepository.save(user);

        // save the activity
        activityService.saveJoinedActivity(user);

        return user;
    }

    public User updatePassword(UpdatePasswordRequest updatePasswordRequest) throws RecordNotFoundException, InvalidConfirmationCodeException {
        User user = getUserByUsernameActive(updatePasswordRequest.getUsername());

        if (user.getConfirmationCode() == null || !user.getConfirmationCode().equals(updatePasswordRequest.getConfirmationCode())) {
            throw new InvalidConfirmationCodeException();
        }

        user.setConfirmationCode(null); // reset confirmation code
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByEnabledTrue();
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

    public User getUserByUsernameAll(String username) throws RecordNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new RecordNotFoundException("User not found with username " + username);
        }
        return user;
    }

    public User getUserByUsernameActive(String username) throws RecordNotFoundException {
        User user = userRepository.findByUsernameAndEnabledTrue(username).orElse(null);
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

            // save the activity
            activityService.saveRolesUpdatedActivity(user);
        }

        return user;
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
        return activityService.getActivitiesForUser(user.getId());
    }

    public List<Activity> activityOfSubscriptionsForCurrentUser(User user) {
        List<Long> subscriptionIds = new ArrayList<>();
        user.getSubscriptions().forEach(e -> subscriptionIds.add(e.getId()));

        return activityService.getActivitiesForUserSubscriptions(subscriptionIds);
    }

    @Async
    public void contactSupport(Contact contact) {
        contactRepository.save(contact);

        // reply email
        emailService.replySupportEmail(contact);

        // email all admins
        List<User> admins = getUserByType(UserType.ADMIN);
        admins.forEach(admin -> emailService.sendSupportEmail(admin.getUsername(), contact));
    }
}
