package edu.baylor.flarn.services;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemSetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemSetService {
    private final ProblemSetRepository problemSetRepository;
    private final KnowledgeSourceRepository knowledgeSourceRepository;

    public ProblemSetService(ProblemSetRepository problemSetRepository, KnowledgeSourceRepository knowledgeSourceRepository) {
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

    public List<ProblemSet> getSolvedProblemsForUser(User user) {
        List<ProblemSet> problemSets = new ArrayList<>();
        for (Session session : user.getParticipatedSessions()) {
            problemSets.add(session.getProblemSet());
        }
        return problemSets;
    }

    public List<Long> getSolvedProblemsIdsForUser(User user) {
        List<Long> problemSetIds = new ArrayList<>();
        for (Session session : user.getParticipatedSessions()) {
            problemSetIds.add(session.getProblemSet().getId());
        }
        return problemSetIds;
    }

    // TODO: throw an error if no problem to solve?
    // returns null if all problems are solved by the user
    public ProblemSet getRandomProblemSet(User user) {
        List<Long> problemSetIds = getSolvedProblemsIdsForUser(user);
        return problemSetRepository.findUnsolved(problemSetIds);
    }
}
