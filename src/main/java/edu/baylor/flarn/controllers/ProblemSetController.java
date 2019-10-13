package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.services.AddProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/problemset")
public class ProblemSetController {

  private final AddProblemSetService addProblemSetService;

  @Autowired
  public ProblemSetController(AddProblemSetService addProblemSetService) {
    this.addProblemSetService = addProblemSetService;
  }

  @GetMapping
  public List<ProblemSet> getProblemSets() {
    return addProblemSetService.getAllProblemSets();
  }

  @GetMapping("{id}")
  public ProblemSet getProblemSetById(@PathVariable long id) {
    return addProblemSetService.getProblemSetById(id);
  }

  @PostMapping
  public ProblemSet createProblemSet(@RequestBody ProblemSet problemSet) {
    return addProblemSetService.createProblemSet(problemSet);
  }
}
