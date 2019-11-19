package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.ProblemSetSearchRequest;
import edu.baylor.flarn.resources.UserRoles;
import edu.baylor.flarn.services.ProblemSetService;
import edu.baylor.flarn.services.SearchProblemSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/problemsets")
@Slf4j
public class ProblemSetController {

    private final ProblemSetService problemSetService;
    private final SearchProblemSetService searchProblemSetService;

    @Autowired
    public ProblemSetController(ProblemSetService problemSetService, SearchProblemSetService searchProblemSetService) {
        this.problemSetService = problemSetService;
        this.searchProblemSetService = searchProblemSetService;
    }

    @GetMapping
    public List<ProblemSet> getProblemSets() {
        return problemSetService.getAllProblemSets();
    }

    @GetMapping("{id}")
    public ProblemSet getProblemSetById(@PathVariable long id) {
        return problemSetService.getProblemSetById(id);
    }

    @PostMapping
    @RolesAllowed(UserRoles.roleModerator)
    public ProblemSet createProblemSet(@RequestBody ProblemSet problemSet, @AuthenticationPrincipal User user) {
        log.info(user.getRoles().toString());
        return problemSetService.createProblemSet(problemSet, user);
    }

    @PostMapping("/search")
    public List<ProblemSet> searchProblemSets(@RequestBody ProblemSetSearchRequest problemSetSearchRequest) {
        return searchProblemSetService.searchProblemSet(problemSetSearchRequest);
    }

    @GetMapping("/random")
    public ProblemSet getRandomProblemSet(@AuthenticationPrincipal User user) {
        return problemSetService.getRandomProblemSet(user);
    }
}
