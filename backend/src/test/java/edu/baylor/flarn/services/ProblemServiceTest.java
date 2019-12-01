package edu.baylor.flarn.services;

import edu.baylor.flarn.exceptions.InvalidConfirmationCodeException;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.*;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains the unit for problem service
 * & Integration test for problem,category,knowledge source,moderator,review,sessions,questions
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

        //get Users; learner and moderator
        User moderator = userService.getUserByUsernameActive("moderator1@gm.com");
        assertThat(moderator.getUserType(), Is.is(UserType.MODERATOR));
        User reviewer = userService.getUserByUsernameActive("admin@gm.com");

        //Knowledge Source
        KnowledgeSource knowledgeSource = new KnowledgeSource();
        knowledgeSource.setContent("Test Knowleged source");

        //Category
        Category category = categoryService.getDefaultCategory();

        //Question
        Question question = new Question();
        question.setContent("Which of these is not a programming language");
        question.setOptions(new ArrayList<String>(){
            {
                add("Java");
                add("C++");
                add("realm");
            }
        });
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
        problem.setReviews(new HashSet<Review>(){
            {
                add(star);
                add(comment);
            }
        });

        Problem saved = problemService.createProblem(problem,moderator);

        assertEquals(saved,problem);
        assertNotNull(problemService.getProblemById(saved.getId()));

        assertTrue(knowledgeSource.getProblem().equals(saved));
//        assertTrue(star.getProblem().equals(saved));
        assertTrue(comment.getProblem().equals(saved));
        assertTrue(question.getProblem().equals(saved));
    }

    @Test
    void getAllProblems() {
        assertNotNull(problemService.getAllProblems());
    }

    @Test
    void getAllUnarchivedProblems() {
    }

    @Test
    void getProblemById() {
    }

    @Test
    void deleteProblem() {
    }

    @Test
    void archiveProblem() {
    }

    @Test
    void updateProblem() {
    }

    @Test
    void deleteBatchProblem() {
    }

    @Test
    void getSolvedProblemsIdsForUser() {
    }

    @Test
    void getRandomProblem() {
        assertNotNull(problemService.getAllProblems());
    }
}