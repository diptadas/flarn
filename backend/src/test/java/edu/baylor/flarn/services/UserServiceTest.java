package edu.baylor.flarn.services;


import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.resources.ConfirmUserRequest;
import edu.baylor.flarn.resources.UserRegistration;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The {@link UserServiceTest} contains the unit & integration tests for the {@link UserService}.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    /***
     * Unit test for find user by Id
     * @throws RecordNotFoundException
     */
    @Test
    void findById() throws RecordNotFoundException {
        User user = userService.findById(1L);
        assertEquals(user.getFullName(), "Admin Mock");
    }

    /***
     * Unit test for active users
     */
    @Test
    void getAllActiveUsers() {
        List<User> activeUsers = userService.getAllActiveUsers();
        activeUsers.forEach(user -> Assert.assertThat(user.isEnabled(), Is.is(true)));
    }


    /***
     * Unit test for user update
     * @throws RecordNotFoundException
     */
    @Test
    public void updateUser() throws RecordNotFoundException {

        // fetch user
        User oldUser = userService.findById(1L);

        // update the fullName and state
        UserRegistration userUpdate = new UserRegistration(oldUser.getUsername(), oldUser.getPassword(), "newName", oldUser.getPhoneNumber(),
                oldUser.getStreet(), oldUser.getCity(), "NY", oldUser.getZip());

        User updatedUser = userService.updateUser(userUpdate, oldUser);

        assertEquals(updatedUser.getFullName(), "newName");
        assertEquals(updatedUser.getState(), "NY");
    }

    /***
     * Unit test for save user
     * @throws RecordNotFoundException
     */
    @Test
    public void saveUser() {
        User user = new User("test" + "@gm.com",
                passwordEncoder.encode("moderator"), "Moderator", "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.LEARNER);
        user.setEnabled(true);
        User saved = userService.saveUser(user);
        assertTrue(saved.equals(user));
    }

    /***
     * Unit test for deactivate user
     * @throws RecordNotFoundException
     */
    @Test
    void deactivateUser() throws RecordNotFoundException {

        User user = userService.findById(1L);

        //deactivate user
        userService.deactivateUser(user);
        assertThatThrownBy(() -> userService.getUserByUsernameActive(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username " + user.getUsername());

    }

    /***
     * Integration test for user CRUD operation
     * @throws RecordNotFoundException
     */
    @Test
    public void CRUDUser() throws RecordNotFoundException {
        User user = new User("test2" + "@gm.com",
                passwordEncoder.encode("moderator"), "Moderator", "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.LEARNER);
        user.setEnabled(true);

        assertThatThrownBy(() -> userService.getUserByUsernameActive(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username " + user.getUsername());

        //save User
        User saved = userService.saveUser(user);

        //Test record
        assertEquals(saved, user);
        assertNotNull(userService.getUserByUsernameActive(user.getUsername()));
        assertTrue(userService.exists(user.getUsername()));


        //deactivate user
        userService.deactivateUser(user);
        assertThatThrownBy(() -> userService.getUserByUsernameActive(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username " + user.getUsername());


    }

    /***
     *  Unit test for subscribing
     * @throws RecordNotFoundException
     */
    @Test
    void follow() throws RecordNotFoundException {
        User subscriber = userService.findById(1L);
        User subscribed = userService.findById(2L);

        subscriber = userService.follow(subscriber, subscribed.getId());

        assertThat(subscriber.getSubscriptions(), contains(subscribed));
    }

    //lazy loading issue needs to be resolved

    /***
     *  Unit test for unsubscribing
     * @throws RecordNotFoundException
     */
    @Test
    void unfollow() throws RecordNotFoundException {

        User subscribed = userService.findById(1L);
        User subscriber = subscribed.getSubscribers().iterator().next();

        subscriber = userService.unfollow(subscriber, subscribed.getId());

        subscribed = userService.findById(1L);

        assertFalse(subscriber.getSubscriptions().contains(subscribed));

        assertFalse(subscribed.getSubscribers().contains(subscriber));
    }

    /***
     * Integration test for subscribe/unsubscribe
     * @throws RecordNotFoundException
     */
    @Test
    void followUnfollow() throws RecordNotFoundException {

        User subscriber = userService.findById(1L);
        User subscribed = userService.findById(2L);

        subscriber = userService.follow(subscriber, subscribed.getId());

        assertTrue(subscriber.getSubscriptions().contains(subscribed));

        assertTrue(subscribed.getSubscribers().contains(subscriber));

        subscriber = userService.unfollow(subscriber, subscribed.getId());

        assertFalse(subscriber.getSubscriptions().contains(subscribed));

        assertFalse(subscribed.getSubscribers().contains(subscriber));

    }


    /***
     * Unit test for user registration
     *
     * @throws RecordNotFoundException
     */
    @Test
    void registerUser() {

        UserRegistration user = new UserRegistration("thelma@gmail.com", "acadia", "Thelma Peters"
                , "255487901", "700 S 7th Street", "Waco", "Texas", "76707");

        userService.registerUser(user);

        //User record exist in database
        assertTrue(userService.exists(user.getUsername()));

        //User is not active/enabled
        assertThatThrownBy(() -> userService.getUserByUsernameActive(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username " + user.getUsername());

    }


    /***
     * Integration test for full registration
     * @throws InvalidConfirmationCodeException
     * @throws RecordNotFoundException
     */
    @Test
    void registration() throws RecordNotFoundException, InvalidConfirmationCodeException {

        UserRegistration registration = new UserRegistration("Thess@gmail.com", "serendipity", "Thesseare Peters"
                , "7826701", "700 S 7th Street", "Waco", "Texas", "76707");

        userService.registerUser(registration);

        //User record exist in database
        assertTrue(userService.exists(registration.getUsername()));
        User user = userService.getUserByUsernameAll(registration.getUsername());

        //User is not active/enabled
        assertThatThrownBy(() -> userService.getUserByUsernameActive(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username " + user.getUsername());

        //set user confirmation code
        user.setConfirmationCode(9090);
        userService.saveUser(user);

        //user confirmation details
        ConfirmUserRequest confirmUserRequest = new ConfirmUserRequest();
        confirmUserRequest.setUsername(user.getUsername());

        //wrong confirmation
        confirmUserRequest.setConfirmationCode(1234);
        assertThatThrownBy(() -> userService.confirmUser(confirmUserRequest)).isInstanceOf(InvalidConfirmationCodeException.class);

        //correct confirmation
        confirmUserRequest.setConfirmationCode(9090);
        userService.confirmUser(confirmUserRequest);
        //user now enabled
        assertNotNull(userService.getUserByUsernameActive(user.getUsername()));

    }


    @Test
    void updatePassword() {

    }

    @Test
    void getUserByType() {
        UserType userType = UserType.ADMIN;
        List<User> users = userService.getUserByType(userType);
        users.forEach(user -> Assert.assertThat(user.getUserType(), Is.is(UserType.ADMIN)));

    }

    @Test
    void changeUserType() throws RecordNotFoundException {

        //New user with type learner
        User user = new User("test2" + "@gm.com",
                passwordEncoder.encode("moderator"), "Moderator", "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.LEARNER);

        //confirm usertype
        assertThat(user.getUserType(), Is.is(UserType.LEARNER));

        user.setUserType(UserType.MODERATOR);

        //confirm change
        assertThat(user.getUserType(), Is.is(UserType.MODERATOR));

    }

    @Test
    void getSolvedProblemsForUser() throws RecordNotFoundException {
        User user = userService.findById(1L);
        List<Problem> problems = userService.getSolvedProblemsForUser(user);
        problems.forEach(problem -> Assert.assertThat(userService.hasAttempted(problem.getId(), user), Is.is(true)));
    }


}