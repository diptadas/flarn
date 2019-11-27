package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import edu.baylor.flarn.repositories.ActivityRepository;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
import edu.baylor.flarn.repositories.QuestionRepository;
import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage problem service includes service for to add problems, delete problems etc.
 */

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;
    private final QuestionRepository questionRepository;
    private final ActivityRepository activityRepository;

    public ProblemService(ProblemRepository problemRepository, KnowledgeSourceRepository knowledgeSourceRepository,
                          QuestionRepository questionRepository, ActivityRepository activityRepository) {
        this.problemRepository = problemRepository;
        this.knowledgeSourceRepository = knowledgeSourceRepository;
        this.questionRepository = questionRepository;
        this.activityRepository = activityRepository;
    }

    public Problem createProblem(Problem problem, User user) {
        problem.setModerator(user);

        // save the knowledge source
        // solved: Not-null property references a transient value - transient instance must be saved before current operation
        knowledgeSourceRepository.save(problem.getKnowledgeSource());

        // save each question
        for (Question question : problem.getQuestions()) {
            questionRepository.save(question);
        }

        // add associations
        problem.getKnowledgeSource().setProblem(problem);
        for (Question question : problem.getQuestions()) {
            question.setProblem(problem);
        }

        problem = problemRepository.save(problem);

        // save the activity
        Activity activity = new Activity(user.getId(), user.getFullName());
        activity.setCreatedProblemActivity(problem.getId(), problem.getTitle());
        activityRepository.save(activity);

        return problem;
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(long id) throws RecordNotFoundException {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem == null) {
            throw new RecordNotFoundException("Problem not found with id: " + id);
        }
        return problem;
    }

    public ResponseBody deleteProblem(Long problem) {
        try {
            problemRepository.deleteById(problem);
            return new ResponseBody(200, "Successful");
        } catch (Exception e) {
            return new ResponseBody(500, e.getMessage());
        }
    }

    public Problem archiveProblem(Long problemId) throws RecordNotFoundException {
        Problem problem = getProblemById(problemId);
        problem.setArchived(true);
        return problemRepository.save(problem);
    }


    public Problem updateProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    @Transactional
    public ResponseBody deleteBatchProblem(List<Long> ids) {
        try {
            problemRepository.deleteByIdIn(ids);
            return new ResponseBody(200, "Successful");
        } catch (Exception e) {
            return new ResponseBody(500, e.getMessage());
        }
    }

    public List<Long> getSolvedProblemsIdsForUser(User user) {
        List<Long> problemIds = new ArrayList<>();
        for (Session session : user.getParticipatedSessions()) {
            problemIds.add(session.getProblem().getId());
        }
        return problemIds;
    }

    // TODO: throw an error if no problem to solve?
    // returns null if all problems are solved by the user
    public Problem getRandomProblem(User user) {
        List<Long> problemIds = getSolvedProblemsIdsForUser(user);
        return problemRepository.findUnsolved(problemIds);
    }

}
