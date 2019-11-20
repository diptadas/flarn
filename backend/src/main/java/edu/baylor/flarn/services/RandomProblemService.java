package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.repositories.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RandomProblemService {

    private final ProblemRepository problemSetRepository;

    public RandomProblemService(ProblemRepository problemSetRepository) {
        this.problemSetRepository = problemSetRepository;
    }

    // TODO: filter unsolved only
    public Problem getRandomProblemSet() {
        long least = 1L;
        long highest = problemSetRepository.count();
        Long random = least + (long) (Math.random() * (highest - least));
        Optional<Problem> problemSet = problemSetRepository.findById(random);
        return problemSet.orElse(null);
    }

}
