package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Profile("!prod")
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EntityManager entityManager, PasswordEncoder passwordEncoder) {
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
        createproblems();
    }

    private void createCategories() {
        for (int i = 0; i < 3; i++) {
            Category category = new Category();
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
                    "temple", "AZ", "0000", UserType.MODERATOR);

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
                    "temple", "AZ", "0000", UserType.LEARNER);

            learner.setEnabled(true);
            entityManager.persist(learner);
            learners.add(learner);
        }
        entityManager.flush(); // flush everything
    }

    private void createAdmin() {
        admin = new User("admin@gm.com",
                passwordEncoder.encode("admin"), "Admin Mock", "254567908", "part",
                "temple", "AZ", "0000", UserType.ADMIN);

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

    private void createproblems() {
        for (int i = 0; i < 10; i++) {
            Problem problem = new Problem();
            problem.setDifficulty(Difficulty.EASY);
            problem.setCategory(categories.get(i % 3)); // add category in cyclic order
            problem.setModerator(moderators.get(i % 3)); // add moderator in cyclic order

            // add knowledge source
            KnowledgeSource knowledgeSource = new KnowledgeSource();
            knowledgeSource.setContentLink("http://google.com");
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

            entityManager.persist(problem);

            problems.add(problem);
        }

        entityManager.flush(); // flush everything
    }
}
