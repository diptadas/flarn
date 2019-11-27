package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.AlreadyStaredException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.ReviewType;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProblemService problemService;

    public ReviewService(ReviewRepository reviewRepository, ProblemService problemService) {
        this.reviewRepository = reviewRepository;
        this.problemService = problemService;
    }

    public Review starProblem(Long problemId, User user) throws RecordNotFoundException, AlreadyStaredException {
        if (hasStared(problemId, user)) {
            throw new AlreadyStaredException("Already stared problem " + problemId);
        }

        Problem problem = problemService.getProblemById(problemId);
        Review review = new Review();
        review.setReviewType(ReviewType.STAR);
        review.setProblem(problem);
        review.setUser(user);
        return reviewRepository.save(review);
    }

    // delete the star entry
    public void unstarProblem(Long problemId, User user) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        reviewRepository.deleteByReviewTypeAndUserAndProblem(ReviewType.STAR, user, problem);
    }

    public boolean hasStared(Long problemId, User user) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        return reviewRepository.findByReviewTypeAndUserAndProblem(ReviewType.STAR, user, problem) != null;
    }

    public Review commentOnProblem(Review review, User user) throws RecordNotFoundException {
        // fetch the complete problem
        review.setProblem(problemService.getProblemById(review.getProblem().getId()));
        review.setUser(user);
        review.setReviewType(ReviewType.COMMENT);
        return reviewRepository.save(review);
    }
}
