package edu.baylor.flarn.controllers;


import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.ProblemSetSearchRequest;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.resources.UserRoles;
import edu.baylor.flarn.services.ManageProblemService;
import edu.baylor.flarn.services.RandomProblemService;
import edu.baylor.flarn.services.SearchProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/problemsets")
@Slf4j
public class ProblemController {

    private final ManageProblemService manageProblemService;
    private final SearchProblemService searchProblemSetService;
    private final RandomProblemService randomProblemSetService;

    @Autowired
    public ProblemController(ManageProblemService manageProblemService,
                             SearchProblemService searchProblemSetService,
                             RandomProblemService randomProblemSetService) {
        this.manageProblemService = manageProblemService;
        this.searchProblemSetService = searchProblemSetService;
        this.randomProblemSetService = randomProblemSetService;
    }

    @GetMapping
    public List<Problem> getProblemSets() {
        return manageProblemService.getAllProblemSets();
    }

    @GetMapping("{id}")
    public Problem getProblemSetById(@PathVariable long id) {
        return manageProblemService.getProblemSetById(id);
    }

    @PostMapping
    @RolesAllowed(UserRoles.roleModerator)
    public Problem createProblemSet(@RequestBody Problem problemSet, @AuthenticationPrincipal User user) {
        // TODO: fix roles allowed restriction not working
        log.info(user.getRoles().toString());
        return manageProblemService.createProblemSet(problemSet, user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or #problem.moderator.username == authentication.name")
    public Problem upDateProblem(@RequestBody Problem problem){
        return manageProblemService.updateProblem(problem);
    }

    @PostMapping("/search")
    public List<Problem> searchProblemSets(@RequestBody ProblemSetSearchRequest problemSetSearchRequest) {
        return searchProblemSetService.searchProblemSet(problemSetSearchRequest);
    }

    //Todo: Check moderator also
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseBody deleteProblem(@PathVariable("id") Long id) {
        return manageProblemService.deleteProblem(id);
    }

    @GetMapping("/random")
    public Problem getRandomProblemSet() {
        return randomProblemSetService.getRandomProblemSet();
    }
}
