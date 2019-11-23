package edu.baylor.flarn.controllers;


import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.ProblemSearchRequest;
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
@RequestMapping("/problems")
@Slf4j
public class ProblemController {

    private final ManageProblemService manageProblemService;
    private final SearchProblemService searchproblemService;
    private final RandomProblemService randomproblemService;

    @Autowired
    public ProblemController(ManageProblemService manageProblemService,
                             SearchProblemService searchproblemService,
                             RandomProblemService randomproblemService) {
        this.manageProblemService = manageProblemService;
        this.searchproblemService = searchproblemService;
        this.randomproblemService = randomproblemService;
    }

    @GetMapping
    public List<Problem> getproblems() {
        return manageProblemService.getAllproblems();
    }

    @GetMapping("{id}")
    public Problem getproblemById(@PathVariable long id) {
        return manageProblemService.getproblemById(id);
    }

    @PostMapping
    @RolesAllowed(UserRoles.roleModerator)
    public Problem createproblem(@RequestBody Problem problem, @AuthenticationPrincipal User user) {
        // TODO: fix roles allowed restriction not working
        log.info(user.getRoles().toString());
        return manageProblemService.createproblem(problem, user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or #problem.moderator.username == authentication.name")
    public Problem upDateProblem(@RequestBody Problem problem){
        return manageProblemService.updateProblem(problem);
    }

    @PostMapping("/search")
    public List<Problem> searchproblems(@RequestBody ProblemSearchRequest problemSearchRequest) {
        return searchproblemService.searchproblem(problemSearchRequest);
    }

    //Todo: Check moderator also
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseBody deleteProblem(@PathVariable("id") Long id) {
        return manageProblemService.deleteProblem(id);
    }

    //Todo: Check moderator also
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseBody deleteBatchProblem(@PathVariable("id") List<Long> ids) {
        return manageProblemService.deleteBatchProblem(ids);
    }

    @GetMapping("/random")
    public Problem getRandomproblem() {
        return randomproblemService.getRandomproblem();
    }
}
