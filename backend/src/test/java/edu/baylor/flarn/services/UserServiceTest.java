package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findById() throws RecordNotFoundException {
        User user = userService.findById(1L);
        assertEquals(user.getFullName(), "Admin Mock");
    }

    @Test
    public void registerUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void follow() {
    }

    @Test
    public void unfollow() {
    }

    @Test
    public void sendConfirmationCode() {
    }

    @Test
    public void confirmUser() {
    }

    @Test
    public void updatePassword() {
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void getAllUsersOrderByPoints() {
    }

    @Test
    public void getSubscriptionsOrderByPoints() {
    }

    @Test
    public void getUserByType() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void getUserByUsername() {
    }

    @Test
    public void changeUserType() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void getSubscribers() {
    }

    @Test
    public void getUserSubscriptions() {
    }

    @Test
    public void searchUserByName() {
    }

    @Test
    public void getSolvedProblemsForUser() {
    }

    @Test
    public void getStaredProblemsForUser() {
    }

    @Test
    public void hasAttempted() {
    }

    @Test
    public void deactivateUser() {
    }

    @Test
    public void activityForCurrentUser() {
    }

    @Test
    public void activityOfSubscriptionsForCurrentUser() {
    }
}