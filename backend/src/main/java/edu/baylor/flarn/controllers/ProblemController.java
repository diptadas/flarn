package edu.baylor.flarn.controllers;


import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.ProblemSearchRequest;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.resources.UserRoles;
import edu.baylor.flarn.services.ProblemService;
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

    private final ProblemService problemService;
    private final SearchProblemService searchProblemService;
    @Autowired
    public ProblemController(ProblemService problemService,
                             SearchProblemService searchproblemService) {
        this.problemService = problemService;
        this.searchProblemService = searchproblemService;
    }

    @GetMapping("")
    public List<Problem> getproblems() {
        return problemService.getAllproblems();
    }

    @GetMapping("{id}")
    public Problem getproblemById(@PathVariable long id) {
        return problemService.getProblemById(id);
    }

    @PostMapping("")
    @RolesAllowed(UserRoles.roleModerator)
    public Problem createproblem(@RequestBody Problem problem, @AuthenticationPrincipal User user) {
        // TODO: fix roles allowed restriction not working
        log.info(user.getRoles().toString());
        return problemService.createProblem(problem, user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or #problem.moderator.username == authentication.name")
    public Problem upDateProblem(@RequestBody Problem problem){
        return problemService.updateProblem(problem);
    }

    @PostMapping("/search")
    public List<Problem> searchProblems(@RequestBody ProblemSearchRequest problemSearchRequest) {
        return searchProblemService.searchProblem(problemSearchRequest);
    }

    //Todo: Check moderator is owner or admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseBody deleteProblem(@PathVariable("id") Long id) {
        return problemService.deleteProblem(id);
    }

    //Todo: Check moderator sole owner or Admin
    @DeleteMapping("/batch/{ids}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseBody deleteBatchProblem(@PathVariable("ids") List<Long> ids) {
        return problemService.deleteBatchProblem(ids);
    }

    @GetMapping("/random")
    public Problem getRandomProblem(@AuthenticationPrincipal User user) {
        return problemService.getRandomProblem(user);
    }
}



