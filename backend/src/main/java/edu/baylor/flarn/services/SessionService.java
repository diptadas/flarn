package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import edu.baylor.flarn.repositories.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Session findById(Long id) throws RecordNotFoundException {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) {
            throw new RecordNotFoundException("can not fetch current user");
        }
        return session;
    }

    public Long getSessionIdForProblemAndUser(Long problemId, Long userId) throws RecordNotFoundException {
        Session session = sessionRepository.findByProblemIdAndUserId(problemId, userId);
        if (session == null) {
            throw new RecordNotFoundException("can not fetch current user");
        }
        return session.getId();
    }

    // user is current authenticated user
    // use it if session payload doesnt contain user
    public Session createSession(Session session, User user) throws RecordNotFoundException {
        if (session.getUser() == null) {
            session.setUser(user); // assign current user
        }

        reFetchUserAndProblem(session);

        session = calculatePointForTheSession(session);
        updateUserPointForSession(session);

        // save the activity
        addActivityForSession(session);

        return session;
    }

    private void reFetchUserAndProblem(Session session) throws RecordNotFoundException {
        Problem problem = session.getProblem();
        User user = session.getUser();

        // re-fetch the user and problem
        user = userService.findById(user.getId());
        problem = problemService.getProblemById(problem.getId());

        // re-assign to session
        session.setUser(user);
        session.setProblem(problem);
    }

    // don't save the activity now
    // save it when ws client is disconnected
    // it may be disconnected via submit button or closing browser window
    @Transactional
    public Session saveSessionForWsClient(Session session) throws RecordNotFoundException {
        reFetchUserAndProblem(session);
        return calculatePointForTheSession(session);
    }

    // called when ws client is disconnected
    @Transactional
    public void updateUserPointForWsClient(Long sessionId) throws RecordNotFoundException {
        Session session = findById(sessionId);
        updateUserPointForSession(session);

        // save the activity
        addActivityForSession(session);
    }

    private Session calculatePointForTheSession(Session session) {
        Problem problem = session.getProblem();
        List<Integer> answers = session.getAnswers();

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

        session.setPointsGained(points);
        session = sessionRepository.save(session);

        log.info("SessionId: " + session.getId() + " points: " + points);

        return session;
    }

    private void updateUserPointForSession(Session session) {
        User user = session.getUser();
        long points = session.getPointsGained();

        user.setPoints(user.getPoints() + points); // increase points

        userService.saveUser(user);

        log.info("user " + user.getUsername() + " gained " + points + " points");
    }

    private void addActivityForSession(Session session) {
        activityService.saveAttemptedProblemActivity(session.getUser(), session.getProblem());
    }
}
