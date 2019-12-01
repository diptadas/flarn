package edu.baylor.flarn.services;


import edu.baylor.flarn.models.Activity;
import edu.baylor.flarn.models.User;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Contains the unit test for Activity
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;


    @Test
    void getActivitiesForUser() {
        List<Activity> userActivities = activityService.getActivitiesForUser(1L);
        userActivities.forEach(activity -> Assert.assertThat(activity.getUserId(), Is.is(1L)));
    }

    @Test
    void getActivitiesForUserSubscriptions(){
        Set<User> subscriptions = userService.getUser(1L).getSubscriptions();
        List<Long> subscriptionIds = new ArrayList<>();

        for(User user: subscriptions){
            subscriptionIds.add(user.getId());
        }

        List<Activity> activities = activityService.getActivitiesForUserSubscriptions(subscriptionIds);
        activities.forEach(activity -> Assert.assertTrue(subscriptionIds.contains(activity.getUserId())));
    }

    @Test
    void saveJoinedActivity() {


    }

    @Test
    void saveUpdatedProfileActivity() {
    }

    @Test
    void saveFollowedActivity() {
    }

    @Test
    void saveAttemptedProblemActivity() {
    }

    @Test
    void saveCreatedProblemActivity() {
    }

    @Test
    void saveStaredActivity() {
    }

    @Test
    void saveCommentedActivity() {
    }
}