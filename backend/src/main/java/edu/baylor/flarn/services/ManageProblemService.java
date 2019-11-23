package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *
 * Manage problem service includes service for to add problems, delete problems etc.
 */

@Service
public class ManageProblemService {
    private final ProblemRepository problemRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;

    public ManageProblemService(ProblemRepository problemRepository, KnowledgeSourceRepository knowledgeSourceRepository) {
        this.problemRepository = problemRepository;
        this.knowledgeSourceRepository = knowledgeSourceRepository;
    }

    public Problem createproblem(Problem problem, User user) {
        problem.setModerator(user);

        // save the knowledge source first
        // solved: Not-null property references a transient value - transient instance must be saved before current operation
        knowledgeSourceRepository.save(problem.getKnowledgeSource());

        return problemRepository.save(problem);
    }

    public List<Problem> getAllproblems() {
        return problemRepository.findAll();
    }

    public Problem getproblemById(long id) {
        Optional<Problem> problem = problemRepository.findById(id);
        return problem.orElse(null);
    }

    public ResponseBody deleteProblem(Long problem){
        try {
            problemRepository.deleteById(problem);
            return  new ResponseBody(200,"Successful");
        }
        catch (Exception e){
            return  new ResponseBody(500,e.getMessage());
        }
    }


    public Problem updateProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    @Transactional
    public ResponseBody deleteBatchProblem(List<Long> ids){
        try {
            problemRepository.deleteByIdIn(ids);
            return  new ResponseBody(200,"Successful");
        }
        catch (Exception e){
            return  new ResponseBody(500,e.getMessage());
        }
    }


}
