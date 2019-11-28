package edu.baylor.flarn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Activity class contains logs of user activities.
 * To avoid redundancy, it don't associate user and problem models.
 * Instead it stores only the part of the models required for timeline.
 * It stores id and name/title of the user/problem.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    ActivityType activityType;

    @NotNull
    Date date;

    @NotNull
    Long userId;
    @NotNull
    String userFullName;

    Long followedUserId;
    String followedUserFullName;

    Long problemId;
    String problemTitle;

    // initializes a activity instance with current date for a user
    public Activity(@NotNull Long userId, @NotNull String userFullName) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.date = new Date();
    }

    // helper to populate data for a commented activity
    public void setCommentedActivity(Long problemId, String problemTitle) {
        this.setActivityType(ActivityType.COMMENTED);
        this.setProblemId(problemId);
        this.setProblemTitle(problemTitle);
    }

    // helper to populate data for a stared activity
    public void setStaredActivity(Long problemId, String problemTitle) {
        this.setActivityType(ActivityType.STARED);
        this.setProblemId(problemId);
        this.setProblemTitle(problemTitle);
    }

    // helper to populate data for a created problem activity
    public void setCreatedProblemActivity(Long problemId, String problemTitle) {
        this.setActivityType(ActivityType.CREATED_PROBLEM);
        this.setProblemId(problemId);
        this.setProblemTitle(problemTitle);
    }

    // helper to populate data for a followed activity
    public void setFollowedActivity(Long followedUserId, String followedUserFullName) {
        this.setActivityType(ActivityType.FOLLOWED);
        this.setFollowedUserId(followedUserId);
        this.setFollowedUserFullName(followedUserFullName);
    }
}
