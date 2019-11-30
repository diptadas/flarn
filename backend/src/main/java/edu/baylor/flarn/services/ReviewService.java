package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.AlreadyStaredException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.ReviewType;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Review service includes CRUD operations for Review model.
 * Currently, deleting comments are not allowed to prevent any misuse of this educational platform.
 * Unstar simply works by deleting the associated star review.
 * It prevents staring a problem if the user already stared the problem.
 * It throws AlreadyStaredException for such attempt.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProblemService problemService;
    private final ActivityService activityService;

    public ReviewService(ReviewRepository reviewRepository, ProblemService problemService, ActivityService activityService) {
        this.reviewRepository = reviewRepository;
        this.problemService = problemService;
        this.activityService = activityService;
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
        activityService.saveStaredActivity(user, problem);

        return review;
    }

    // delete the star entry
    public void unstarProblem(Long problemId, User user) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        reviewRepository.deleteByReviewTypeAndUserAndProblem(ReviewType.STAR, user, problem);
    }

    public boolean hasStared(Long problemId, User user) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        List<Review> reviews = reviewRepository.findByReviewTypeAndUserAndProblem(ReviewType.STAR, user, problem);
        return reviews != null && reviews.size() > 0;
    }

    public Review commentOnProblem(Review review, User user) throws RecordNotFoundException {
        // fetch the complete problem
        Problem problem = problemService.getProblemById(review.getProblem().getId());
        review.setProblem(problem);
        review.setUser(user);
        review.setReviewType(ReviewType.COMMENT);
        review = reviewRepository.save(review);

        // save the activity
        activityService.saveCommentedActivity(user, problem);

        return review;
    }

    public Long countStarsForProblem(long problemId) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        return reviewRepository.countByReviewTypeAndProblem(ReviewType.STAR, problem);
    }

    public List<Review> commentsForProblem(long problemId) throws RecordNotFoundException {
        Problem problem = problemService.getProblemById(problemId);
        return reviewRepository.findByReviewTypeAndProblem(ReviewType.COMMENT, problem);
    }
}
