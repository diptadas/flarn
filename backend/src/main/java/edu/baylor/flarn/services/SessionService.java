package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import edu.baylor.flarn.repositories.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Session service includes the create operation for the Session model.
 * Whenever a session is submitted, it also calculates the result and update the points of the user.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Service
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;
    private final ProblemService problemService;
    private final ActivityService activityService;

    public SessionService(SessionRepository sessionRepository, UserService userService, ProblemService problemService, ActivityService activityService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.problemService = problemService;
        this.activityService = activityService;
    }

    public Session createSession(Session session, User user) throws RecordNotFoundException {
        if (session.getUser() == null) { // set current logged in user
            session.setUser(user);
        }

        updatePointForTheSession(session);
        session = sessionRepository.save(session);

        // save the activity
        activityService.saveAttemptedProblemActivity(session.getUser(), session.getProblem());

        return session;
    }

    private void updatePointForTheSession(Session session) throws RecordNotFoundException {
        User user = session.getUser();
        Problem problem = session.getProblem();
        List<Integer> answers = session.getAnswers();

        // re-fetch the user and problem
        user = userService.findById(user.getId());
        problem = problemService.getProblemById(problem.getId());

        // update points based on difficulty
        // 3 for hard, 2 for medium, 1 for easy
        long pointForEachQuestion = 0;

        if (problem.getDifficulty().equals(Difficulty.HARD)) {
            pointForEachQuestion = 3;
        } else if (problem.getDifficulty().equals(Difficulty.MEDIUM)) {
            pointForEachQuestion = 2;
        } else {
            pointForEachQuestion = 1;
        }

        long points = 0;
        int index = 0;

        for (Question question : problem.getQuestions()) {
            // this should not happen, still check to avoid IndexOutOfBound
            if (answers.size() == index) {
                break;
            }

            // add correct answers in session
            session.getCorrectAnswers().add(question.getAnswer());

            // answer matched, update points
            if (question.getAnswer() == answers.get(index)) {
                points += pointForEachQuestion;
            }
            // else do nothing, no negative points, we are not evil

            index++;
        }

        user.setPoints(user.getPoints() + points); // increase points
        userService.saveUser(user);
        log.info("user " + user.getUsername() + " gained " + points + " points");

        session.setPointsGained(points);
    }
}
