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
 * To avoid redundancy, it doesn't associate user and problem models.
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
    public Activity(User user) {
        this.userId = user.getId();
        this.userFullName = user.getFullName();
        this.date = new Date();
    }

    // initializes a activity instance with current date for a user related to a problem
    public Activity(User user, Problem problem) {
        this(user);
        this.problemId = problem.getId();
        this.problemTitle = problem.getTitle();
    }
}
