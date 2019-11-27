package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.AlreadyStaredException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import edu.baylor.flarn.repositories.ActivityRepository;
import edu.baylor.flarn.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProblemService problemService;
    private final ActivityRepository activityRepository;

    public ReviewService(ReviewRepository reviewRepository, ProblemService problemService, ActivityRepository activityRepository) {
        this.reviewRepository = reviewRepository;
        this.problemService = problemService;
        this.activityRepository = activityRepository;
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
        review = reviewRepository.save(review);

        // save the activity
        Activity activity = new Activity(user.getId(), user.getFullName());
        activity.setStaredActivity(problem.getId(), problem.getTitle());
        activityRepository.save(activity);

        return review;
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
        Problem problem = problemService.getProblemById(review.getProblem().getId());
        review.setProblem(problem);
        review.setUser(user);
        review.setReviewType(ReviewType.COMMENT);
        review = reviewRepository.save(review);

        // save the activity
        Activity activity = new Activity(user.getId(), user.getFullName());
        activity.setCommentedActivity(problem.getId(), problem.getTitle());
        activityRepository.save(activity);

        return review;
    }

    public Long countStarsForProblem(long problemId) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        return reviewRepository.countByReviewTypeAndProblem(ReviewType.STAR, problem);
    }
}
