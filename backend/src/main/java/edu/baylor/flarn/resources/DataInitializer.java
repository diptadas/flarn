package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.Category;
import edu.baylor.flarn.models.Problem;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.models.KnowledgeSource;
import edu.baylor.flarn.models.Question;
import edu.baylor.flarn.models.Review;
import edu.baylor.flarn.models.ReviewType;
import edu.baylor.flarn.models.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link DataInitializer} class populates the initial data for the dev environment.
 * It won't populate data in the prod environment.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Profile("!prod")
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final String image = "https://i.ibb.co/kgLYxgp/default-profile.png";

    /**
     * @param entityManager   entity manager
     * @param passwordEncoder used to encode the password
     */
    public DataInitializer(final EntityManager entityManager, final PasswordEncoder passwordEncoder){
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    private List<Category> categories = new ArrayList<>();
    private List<Problem> problems = new ArrayList<>();
    private List<User> moderators = new ArrayList<>();
    private List<User> learners = new ArrayList<>();
    private User admin;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Initializing data");

        createAdmin();
        createModerators();
        createLearners();
        createSubscriptions();

        createCategories();
        createProblems();
    }

    private void createCategories() {
        // create default category
        Category category = new Category();
        category.setName(Category.DEFAULT_CATEGORY_NAME);
        entityManager.persist(category);

        // create 3 other categories
        for (int i = 0; i < 3; i++) {
            category = new Category();
            category.setName("category-" + i);
            entityManager.persist(category);

            categories.add(category);
        }

        entityManager.flush(); // flush everything
    }

    private void createModerators() {
        for (int i = 0; i < 3; i++) {
            User moderator = new User("moderator" + i + "@gm.com",
                    passwordEncoder.encode("moderator" + i), "Moderator" + i, "254567908", "part",
                    "temple", "AZ", "0000", "my story", image, image, UserType.MODERATOR);

            moderator.setEnabled(true);
            entityManager.persist(moderator);
            moderators.add(moderator);
        }
        entityManager.flush(); // flush everything
    }

    private void createLearners() {
        for (int i = 0; i < 3; i++) {
            User learner = new User("learner" + i + "@gm.com",
                    passwordEncoder.encode("learner" + i), "Learner" + i, "254567908", "part",
                    "temple", "AZ", "0000", "my story", image, image, UserType.LEARNER);

            learner.setEnabled(true);
            entityManager.persist(learner);
            learners.add(learner);
        }
        entityManager.flush(); // flush everything
    }

    private void createAdmin() {
        admin = new User("admin@gm.com",
                passwordEncoder.encode("admin"), "Admin Mock", "254567908", "part",
                "temple", "AZ", "0000", "my story", image, image, UserType.ADMIN);

        admin.setEnabled(true);
        entityManager.persist(admin);
        entityManager.flush(); // flush everything
    }

    private void createSubscriptions() {
        moderators.get(1).subscribe(moderators.get(0));
        entityManager.persist(moderators.get(1)); // don't need to persist both

        learners.get(0).subscribe(moderators.get(1));
        learners.get(0).subscribe(admin);
        entityManager.persist(learners.get(0)); // don't need to persist both

        learners.get(1).subscribe(learners.get(0));
        learners.get(1).subscribe(moderators.get(1));
        entityManager.persist(learners.get(1)); // don't need to persist both

        learners.get(2).subscribe(learners.get(0));
        learners.get(2).subscribe(learners.get(1));
        entityManager.persist(learners.get(1)); // don't need to persist both

        entityManager.flush(); // flush everything
    }

    private void createProblems() {
        for (int i = 0; i < 10; i++) {
            Problem problem = new Problem();
            problem.setDifficulty(Difficulty.EASY);
            problem.setCategory(categories.get(i % 3)); // add category in cyclic order
            problem.setModerator(moderators.get(i % 3)); // add moderator in cyclic order

            // add knowledge source
            KnowledgeSource knowledgeSource = new KnowledgeSource();
            knowledgeSource.setContent("http://google.com");
            entityManager.persist(knowledgeSource);

            problem.setKnowledgeSource(knowledgeSource);
            problem.setTitle("problem-" + i);
            problem.setDescription("This is problem-" + i);

            // add questions
            for (int j = 0; j < 3; j++) {
                Question question = new Question();
                question.setContent("What is question-" + j);
                question.setAnswer(j);

                // add options
                for (int k = 0; k < 4; k++) {
                    question.getOptions().add("option-" + k);
                }

                entityManager.persist(question);

                problem.addQuestion(question);
            }

            // add reviews

            // add one comment from each moderator
            for (int j = 0; j < 3; j++) {
                Review review = new Review();
                review.setReviewType(ReviewType.COMMENT);
                review.setCommentContent("I loved this problem");
                review.setUser(moderators.get(j));

                entityManager.persist(review);
                problem.addReview(review);
            }

            // add one star from each learner
            for (int j = 0; j < 3; j++) {
                Review review = new Review();
                review.setReviewType(ReviewType.STAR);
                review.setUser(learners.get(j));

                entityManager.persist(review);
                problem.addReview(review);
            }

            entityManager.persist(problem);

            problems.add(problem);
        }

        entityManager.flush(); // flush everything
    }
}
