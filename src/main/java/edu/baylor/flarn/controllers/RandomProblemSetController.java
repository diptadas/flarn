package edu.baylor.flarn.controllers;


import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.services.GetRandomProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/randomproblemset")
public class RandomProblemSetController {

    @Autowired
    GetRandomProblemSetService getRandomProblemSetService;

    @RequestMapping("")
    public Optional<ProblemSet> getRandomProblemSet() {

        Optional<ProblemSet> problemSet = getRandomProblemSetService.getRandomProblemSet();
        return problemSet;
    }


}
