package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.Question;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
import edu.baylor.flarn.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem service includes CRUD operations for Problem model.
 * However, it don't allow any delete operation.
 * Instead it archives the problem.
 * It excludes the archived problems whenever list of all problems are queried.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;
    private final QuestionRepository questionRepository;
    private final ActivityService activityService;
    private final CategoryService categoryService;

    public ProblemService(ProblemRepository problemRepository, KnowledgeSourceRepository knowledgeSourceRepository,
                          QuestionRepository questionRepository, ActivityService activityService, CategoryService categoryService) {
        this.problemRepository = problemRepository;
        this.knowledgeSourceRepository = knowledgeSourceRepository;
        this.questionRepository = questionRepository;
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    public Problem createProblem(Problem problem, User user) throws RecordNotFoundException {
        // get the category by name if id not present
        if (problem.getCategory().getId() == null) {
            Category category = categoryService.getCategoryByName(problem.getCategory().getName());
            problem.setCategory(category);
        }

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
        activityService.saveCreatedProblemActivity(user, problem);

        return problem;
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public List<Problem> getAllUnarchivedProblems() {
        return problemRepository.findByArchivedFalse();
    }

    public Problem getProblemById(long id) throws RecordNotFoundException {
        Problem problem = problemRepository.findByIdAndArchivedFalse(id).orElse(null);
        if (problem == null) {
            throw new RecordNotFoundException("Problem not found with id: " + id);
        }
        return problem;
    }

    public Problem archiveProblem(Long problemId) throws RecordNotFoundException {
        Problem problem = getProblemById(problemId);
        problem.setArchived(true);
        return problemRepository.save(problem);
    }


    public Problem updateProblem(Problem problem) {
        return problemRepository.save(problem);
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
        if(problemIds.size() > 0) {
            return problemRepository.findUnsolvedRandom(problemIds);
        } else {
            return problemRepository.findRandom();
        }
    }

    public List<Problem> getModeratorProblems(User user) {
        return problemRepository.findAllByModeratorId(user.getId());
    }

}
