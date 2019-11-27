package edu.baylor.flarn.controllers;


import edu.baylor.flarn.exceptions.AlreadyStaredException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.services.ReviewService;
import edu.baylor.flarn.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    // current user stars the problem
    @GetMapping("/star")
    public Review starProblem(@RequestParam("problemId") Long problemId, @AuthenticationPrincipal User user) throws RecordNotFoundException, AlreadyStaredException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return reviewService.starProblem(problemId, user);
    }

    // current user unstars the problem
    @GetMapping("/unstar")
    public void unstarProblem(@RequestParam("problemId") Long problemId, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        reviewService.unstarProblem(problemId, user);
    }

    // returns true if current user stared the problem
    @GetMapping("/hasStared")
    public boolean hasStaredProblem(@RequestParam("problemId") Long problemId, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        // re-fetch the current user
        // fixes error: failed to lazily initialize
        user = userService.findById(user.getId());
        return reviewService.hasStared(problemId, user);
    }
}



