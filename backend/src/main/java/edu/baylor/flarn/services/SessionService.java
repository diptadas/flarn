package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import edu.baylor.flarn.repositories.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;
    private final ProblemService problemService;

    public SessionService(SessionRepository sessionRepository, UserService userService, ProblemService problemService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.problemService = problemService;
    }

    public Session createSession(Session session, User user) throws RecordNotFoundException {
        if (session.getUser() == null) { // set current logged in user
            session.setUser(user);
        }

        session = sessionRepository.save(session);
        updatePointForTheSession(user, session.getProblem(), session.getAnswers());

        return session;
    }

    private void updatePointForTheSession(User user, Problem problem, List<Integer> answers) throws RecordNotFoundException {
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

            // answer matched, update points
            if (question.getAnswer() == answers.get(index)) {
                points += pointForEachQuestion;
            }
            // else do nothing, no negative points, we are not evil

            index++;
        }

        user.setPoints(points);
        userService.saveUser(user);
        log.info("user " + user.getUsername() + " gained " + points + " points");
    }
}
