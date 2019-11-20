package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemRepository;
import edu.baylor.flarn.resources.ResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * Manage problem service includes service for to add problems, delete problems etc.
 */

@Service
public class ManageProblemService {
    private final ProblemRepository problemSetRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;

    public ManageProblemService(ProblemRepository problemSetRepository, KnowledgeSourceRepository knowledgeSourceRepository) {
        this.problemSetRepository = problemSetRepository;
        this.knowledgeSourceRepository = knowledgeSourceRepository;
    }

    public Problem createProblemSet(Problem problemSet, User user) {
        problemSet.setModerator(user);

        // save the knowledge source first
        // solved: Not-null property references a transient value - transient instance must be saved before current operation
        knowledgeSourceRepository.save(problemSet.getKnowledgeSource());

        return problemSetRepository.save(problemSet);
    }

    public List<Problem> getAllProblemSets() {
        return problemSetRepository.findAll();
    }

    public Problem getProblemSetById(long id) {
        Optional<Problem> problemSet = problemSetRepository.findById(id);
        return problemSet.orElse(null);
    }

    public ResponseBody deleteProblem(Long problem){
        try {
            problemSetRepository.deleteById(problem);
            return  new ResponseBody(200,"Successful");
        }
        catch (Exception e){
            return  new ResponseBody(500,e.getMessage());
        }
    }
}
