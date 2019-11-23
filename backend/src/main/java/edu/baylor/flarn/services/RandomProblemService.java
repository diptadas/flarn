package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.repositories.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RandomProblemService {

    private final ProblemRepository problemRepository;

    public RandomProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    // TODO: filter unsolved only
    public Problem getRandomproblem() {
        long least = 1L;
        long highest = problemRepository.count();
        Long random = least + (long) (Math.random() * (highest - least));
        Optional<Problem> problem = problemRepository.findById(random);
        return problem.orElse(null);
    }

}
