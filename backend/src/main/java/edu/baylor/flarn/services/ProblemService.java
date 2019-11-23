package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manage problem service includes service for to add problems, delete problems etc.
 */

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;

    public ProblemService(ProblemRepository problemRepository, KnowledgeSourceRepository knowledgeSourceRepository) {
        this.problemRepository = problemRepository;
        this.knowledgeSourceRepository = knowledgeSourceRepository;
    }

    public Problem createProblem(Problem problem, User user) {
        problem.setModerator(user);

        // save the knowledge source first
        // solved: Not-null property references a transient value - transient instance must be saved before current operation
        knowledgeSourceRepository.save(problem.getKnowledgeSource());

        return problemRepository.save(problem);
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(long id) {
        Optional<Problem> problem = problemRepository.findById(id);
        return problem.orElse(null);
    }

    public ResponseBody deleteProblem(Long problem) {
        try {
            problemRepository.deleteById(problem);
            return new ResponseBody(200, "Successful");
        } catch (Exception e) {
            return new ResponseBody(500, e.getMessage());
        }
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
