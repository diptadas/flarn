package edu.baylor.flarn.repositories;

import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.ReviewType;
import edu.baylor.flarn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    void deleteByReviewTypeAndUserAndProblem(ReviewType reviewType, User user, Problem problem);

    List<Review> findByReviewTypeAndUserAndProblem(ReviewType reviewType, User user, Problem problem);

    List<Review> findByReviewTypeAndProblem(ReviewType reviewType, Problem problem);

    Long countByReviewTypeAndProblem(ReviewType reviewType, Problem problem);
}
