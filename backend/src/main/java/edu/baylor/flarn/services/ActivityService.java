package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Activity;
import edu.baylor.flarn.models.ActivityType;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getActivitiesForUser(Long userId) {
        return activityRepository.findByUserIdOrderByDateDesc(userId);
    }

    public List<Activity> getActivitiesForUserSubscriptions(List<Long> subscriptionIds) {
        return activityRepository.findByUserIdInOrderByDateDesc(subscriptionIds);
    }

    public void saveJoinedActivity(User user) {
        Activity activity = new Activity(user);
        activity.setActivityType(ActivityType.JOINED);
        activityRepository.save(activity);
    }

    public void saveUpdatedProfileActivity(User user) {
        Activity activity = new Activity(user);
        activity.setActivityType(ActivityType.UPDATED_PROFILE);
        activityRepository.save(activity);
    }

    public void saveFollowedActivity(User user, User followedUser) {
        Activity activity = new Activity(user);
        activity.setActivityType(ActivityType.FOLLOWED);
        activity.setFollowedUserId(followedUser.getId());
        activity.setFollowedUserFullName(followedUser.getFullName());
        activityRepository.save(activity);
    }

    public void saveAttemptedProblemActivity(User user, Problem problem) {
        Activity activity = new Activity(user, problem);
        activity.setActivityType(ActivityType.ATTEMPTED_PROBLEM);
        activityRepository.save(activity);
    }

    public void saveCreatedProblemActivity(User user, Problem problem) {
        Activity activity = new Activity(user, problem);
        activity.setActivityType(ActivityType.CREATED_PROBLEM);
        activityRepository.save(activity);

    }

    public void saveStaredActivity(User user, Problem problem) {
        Activity activity = new Activity(user, problem);
        activity.setActivityType(ActivityType.STARED);
        activityRepository.save(activity);
    }

    public void saveCommentedActivity(User user, Problem problem) {
        Activity activity = new Activity(user, problem);
        activity.setActivityType(ActivityType.COMMENTED);
        activityRepository.save(activity);
    }
}