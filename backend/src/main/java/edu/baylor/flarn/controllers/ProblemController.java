package edu.baylor.flarn.controllers;


import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.resources.ProblemSearchRequest;
import edu.baylor.flarn.resources.ResponseBody;
import edu.baylor.flarn.resources.UserRoles;
import edu.baylor.flarn.services.ProblemService;
import edu.baylor.flarn.services.SearchProblemService;
import edu.baylor.flarn.services.UserService;
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
    private final UserService userService;

    @Autowired
    public ProblemController(ProblemService problemService,
                             SearchProblemService searchproblemService,
                             UserService userService) {
        this.problemService = problemService;
        this.searchProblemService = searchproblemService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<Problem> getProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("{id}")
    public Problem getProblemById(@PathVariable long id) throws RecordNotFoundException {
        return problemService.getProblemById(id);
    }

    @PostMapping("")
    @RolesAllowed(UserRoles.roleModerator)
    public Problem createProblem(@RequestBody Problem problem, @AuthenticationPrincipal User user) {
        // TODO: fix roles allowed restriction not working
        log.info(user.getRoles().toString());
        return problemService.createProblem(problem, user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or #problem.moderator.username == authentication.name")
    public Problem updateProblem(@RequestBody Problem problem) {
        return problemService.updateProblem(problem);
    }

    @PostMapping("/search")
    public List<Problem> searchProblems(@RequestBody ProblemSearchRequest problemSearchRequest) {
        return searchProblemService.searchProblem(problemSearchRequest);
    }

    @GetMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    public Problem archiveProblem(@PathVariable("id") Long id) throws RecordNotFoundException {
        return problemService.archiveProblem(id);
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
    public Problem getRandomProblem(@AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return problemService.getRandomProblem(user);
    }

    // current user stars the problem
    @GetMapping("/{id}/star")
    public Problem starProblem(@PathVariable("id") Long id, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return problemService.getRandomProblem(user);
    }
}



