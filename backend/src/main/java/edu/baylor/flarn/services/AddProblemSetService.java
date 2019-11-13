package edu.baylor.flarn.services;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddProblemSetService {
    private final ProblemSetRepository problemSetRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;

    public AddProblemSetService(ProblemSetRepository problemSetRepository, KnowledgeSourceRepository knowledgeSourceRepository) {
        this.problemSetRepository = problemSetRepository;
        this.knowledgeSourceRepository = knowledgeSourceRepository;
    }

    public ProblemSet createProblemSet(ProblemSet problemSet, User user) {
        problemSet.setModerator(user);

        // save the knowledge source first
        // solved: Not-null property references a transient value - transient instance must be saved before current operation
        knowledgeSourceRepository.save(problemSet.getKnowledgeSource());

        return problemSetRepository.save(problemSet);
    }

    public List<ProblemSet> getAllProblemSets() {
        return problemSetRepository.findAll();
    }

    public ProblemSet getProblemSetById(long id) {
        Optional<ProblemSet> problemSet = problemSetRepository.findById(id);
        return problemSet.orElse(null);
    }
}
