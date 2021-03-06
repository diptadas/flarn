package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.AlreadyStaredException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The {@link ReviewServiceTest} contains the unit tests for the {@link ReviewService}.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Test
    void starProblem() throws RecordNotFoundException, AlreadyStaredException {
        // get problem
        List<Problem> problems = problemService.getAllUnarchivedProblems();
        Problem problem = problems.get(0);

        User user = userService.findById(1L);

        Review review = reviewService.starProblem(problem.getId(), user);
        assertNotNull(review, "review object should not be null");

        // if we try to star the problem again, it should throw record not found exception
        assertThatThrownBy(() -> reviewService.starProblem(problem.getId(), user)).isInstanceOf(AlreadyStaredException.class).hasMessageContaining("Already stared problem " + problem.getId());
    }

    @Test
    void unstarProblem() throws RecordNotFoundException, AlreadyStaredException {
        // get problem
        List<Problem> problems = problemService.getAllUnarchivedProblems();
        Problem problem = problems.get(0);

        User user = userService.findById(1L);

        Review review = reviewService.starProblem(problem.getId(), user);
        assertNotNull(review, "review object should not be null");

        assertTrue(reviewService.hasStared(problem.getId(), user));

        reviewService.unstarProblem(problem.getId(), user);
        assertFalse(reviewService.hasStared(problem.getId(), user));
    }

}
