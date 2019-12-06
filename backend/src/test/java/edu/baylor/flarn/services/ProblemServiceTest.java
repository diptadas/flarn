package edu.baylor.flarn.services;


import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.Question;
import edu.baylor.flarn.models.KnowledgeSource;
import edu.baylor.flarn.models.UserType;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.ReviewType;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link ProblemServiceTest} contains the unit tests for the {@link ProblemService}.
 * Also integrations test for problem, category, knowledge-source, moderator, review, sessions, and questions.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Test
    /**
     * This test method is contains
     * Integration test for: problem,category,knowledge source,moderator,review,sessions,questions
     */
    void createProblem() throws RecordNotFoundException {

        // get Users: learner and moderator
        User moderator = userService.getUserByUsernameActive("moderator1@gm.com");
        assertThat("User has moderator role", moderator.getUserType(), Is.is(UserType.MODERATOR));
        User reviewer = userService.getUserByUsernameActive("admin@gm.com");

        // Knowledge Source
        KnowledgeSource knowledgeSource = new KnowledgeSource();
        knowledgeSource.setContent("Test Knowledge source");

        // Category
        Category category = categoryService.getDefaultCategory();

        // Question
        Question question = new Question();
        question.setContent("Which of these is not a programming language");
        List<String> options = new ArrayList<>();
        options.add("Java");
        options.add("C++");
        options.add("realm");
        question.setOptions(options);
        question.setAnswer(2);

        Review star = new Review();
        star.setUser(reviewer);
        star.setReviewType(ReviewType.STAR);

        Review comment = new Review();
        comment.setUser(reviewer);
        comment.setReviewType(ReviewType.COMMENT);
        comment.setCommentContent("This problem is ....");

        Problem problem = new Problem();
        problem.setDifficulty(Difficulty.EASY);
        problem.setTitle("Test problem");
        problem.setDescription("This is a problem for test");
        problem.setArchived(false);

        problem.setKnowledgeSource(knowledgeSource);
        problem.setCategory(category);
        problem.getQuestions().add(question);
        Set<Review> review = new HashSet<Review>();
        review.add(star);
        review.add(comment);
        problem.setReviews(review);

        Problem saved = problemService.createProblem(problem, moderator);

        assertEquals(saved, problem, saved.getTitle() + " Expected");
        assertNotNull(problemService.getProblemById(saved.getId()), "Should not be null");

        assertEquals(knowledgeSource.getProblem(), saved, "");
        assertEquals(saved, question.getProblem(), saved.getTitle() + " expected");
    }

    @Test
    void getAllProblems() {
        assertNotNull(problemService.getAllProblems(), "Should not be null");
    }

    @Test
    void getAllUnarchivedProblems() {
        List<Problem> problems = problemService.getAllUnarchivedProblems();
        problems.forEach(problem -> Assert.assertThat(problem.isArchived(), Is.is(false)));
    }


    @Test
    void archiveProblem() throws RecordNotFoundException {
        List<Problem> problems = problemService.getAllUnarchivedProblems();
        Problem problem = problems.get(0);

        // archive problem
        problemService.archiveProblem(problem.getId());

        // it should throw record not found exception
        assertThatThrownBy(() -> problemService.getProblemById(problem.getId())).isInstanceOf(RecordNotFoundException.class).hasMessageContaining("Problem not found with id: " + problem.getId());
    }

    @Test
    void getRandomProblem() throws RecordNotFoundException {
        // get User
        User user = userService.getUserByUsernameActive("moderator1@gm.com");

        Problem problem = problemService.getRandomProblem(user);
        assertNotNull(problem, "Should not be null");
        assertFalse(problem.isArchived(), "Should return false");
    }
}