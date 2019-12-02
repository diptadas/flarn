package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.resources.ProblemSearchRequest;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The {@link SearchProblemServiceTest} contains the unit & integration tests for the SearchProblemService.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class SearchProblemServiceTest {

    @Autowired
    private SearchProblemService searchProblemService;

    @Test
    void searchProblem() {

        //Problem request

        ProblemSearchRequest problemSearchRequest = new ProblemSearchRequest();
        problemSearchRequest.setCategory(Category.DEFAULT_CATEGORY_NAME);
        problemSearchRequest.setDifficulty(Difficulty.HARD.name());
        problemSearchRequest.setTitle("");

        List<Problem> problems = searchProblemService.searchProblem(problemSearchRequest);
        // using title "", so it should not return null
        assertNotNull(problems,"problem should not be null");
        problems.forEach(problem -> Assert.assertThat(problem.getCategory(), Is.is(Category.DEFAULT_CATEGORY_NAME)));
        problems.forEach(problem -> Assert.assertThat(problem.getDifficulty(), Is.is(Difficulty.HARD.name())));
    }


}