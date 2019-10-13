package edu.baylor.flarn.services;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.repositories.ProblemSetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AddProblemSetService {
  private final ProblemSetRepository problemSetRepository;

  public AddProblemSetService(ProblemSetRepository problemSetRepository) {
    this.problemSetRepository = problemSetRepository;
  }

  public ProblemSet createProblemSet(@RequestBody ProblemSet problemSet) {
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
