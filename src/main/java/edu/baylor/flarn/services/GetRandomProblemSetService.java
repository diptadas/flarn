package edu.baylor.flarn.services;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.repositories.ProblemSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetRandomProblemSetService {

    @Autowired
    ProblemSetRepository problemSetRepository;


    public Optional<ProblemSet> getRandomProblemSet() {
        long least = 1L;
        long highest = problemSetRepository.count();
        Long random = least + (long) (Math.random() * (highest - least));
        Optional<ProblemSet> problemSet = problemSetRepository.findById(random);
        return problemSet;
    }

}
