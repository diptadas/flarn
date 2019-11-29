package edu.baylor.flarn.services;


import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.resources.UserRegistration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findById() throws RecordNotFoundException {
        User user = userService.findById(1L);
        assertEquals(user.getFullName(), "Admin Mock");
    }

    @Test
    public void updateUser() throws RecordNotFoundException {
        User oldUser = userService.findById(1L);
        UserRegistration userUpdate= new UserRegistration("newUsername","newPassword","newName",oldUser.getPhoneNumber(),oldUser.getStreet(),oldUser.getCity(),oldUser.getState(),oldUser.getZip());
        User updateUser = userService.updateUser(userUpdate,oldUser);
        assertFalse(updateUser.equals(oldUser));
    }

    @Test
    public void saveUser() {
        User user = new User("test"  + "@gm.com",
                passwordEncoder.encode("moderator" ), "Moderator" , "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.LEARNER);
        user.setEnabled(true);
        User saved = userService.saveUser(user);
        assertTrue(saved.equals(user));
    }

    @Test
    public void deleteUser() {
    }

    // create,read,update,delete test
    @Test
    public void CRUDUser() throws RecordNotFoundException {
        User user = new User("test2"  + "@gm.com",
                passwordEncoder.encode("moderator" ), "Moderator" , "254567908", "part",
                "temple", "AZ", "0000", "my story", null, null, UserType.LEARNER);
        user.setEnabled(true);

        assertThatThrownBy(() -> userService.getUserByUsername(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username "+user.getUsername());

        //save User
        User saved = userService.saveUser(user);

        //Test record
        assertEquals(saved,user);
        assertNotNull(userService.getUserByUsername(user.getUsername()));


        //delete user
        ResponseBody response =  userService.deleteUser(saved.getId());
        assertThatThrownBy(() -> userService.getUserByUsername(user.getUsername())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("User not found with username "+user.getUsername());

    }

    @Test
    void exists() {
    }

    @Test
    void registerUser() {
    }

    @Test
    void follow() {
    }

    @Test
    void unfollow() {
    }

    @Test
    void sendConfirmationCode() {
    }

    @Test
    void confirmUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getAllUsersOrderByPoints() {
    }

    @Test
    void getSubscriptionsOrderByPoints() {
    }

    @Test
    void getUserByType() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void changeUserType() {
    }

    @Test
    void getSubscribers() {
    }

    @Test
    void getUserSubscriptions() {
    }

    @Test
    void searchUserByName() {
    }

    @Test
    void getSolvedProblemsForUser() {
    }

    @Test
    void getStaredProblemsForUser() {
    }

    @Test
    void hasAttempted() {
    }

    @Test
    void deactivateUser() {
    }

    @Test
    void activityForCurrentUser() {
    }

    @Test
    void activityOfSubscriptionsForCurrentUser() {
    }
}