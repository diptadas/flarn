package edu.baylor.flarn;

import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.KnowledgeSource;
import edu.baylor.flarn.models.Option;
import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.Question;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootApplication
public class FlarnApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlarnApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(
    EntityManager entityManager) {
    return new CommandLineRunner() {
      @Override
      @Transactional
      public void run(String... args) {
        System.out.println("Initializing data");

        // save some knowledge sources
        KnowledgeSource knowledgeSource = new KnowledgeSource();
        knowledgeSource.setContentLink("http://google.com");
        entityManager.persist(knowledgeSource);

        // save a couple of problem sets
        ProblemSet problemSet1 = new ProblemSet();
        problemSet1.setKnowledgeSource(knowledgeSource);
        problemSet1.setTitle("Problem Set 1");
        problemSet1.setDescription("Here we are eating potatoes!");

        // create Questions
        Question question1 = new Question();
        question1.setContent("How are you doing?");

        Option option1 = new Option("fine");
        entityManager.persist(option1);
        question1.getOptions().add(option1);

        Option option2 = new Option("tired");
        entityManager.persist(option2);
        question1.getOptions().add(option2);

        Option option3 = new Option("hungry");
        entityManager.persist(option3);
        question1.getOptions().add(option3);

        entityManager.persist(question1);

        problemSet1.setDifficulty(Difficulty.EASY);
        problemSet1.getQuestion().add(question1);
        entityManager.persist(problemSet1);
      }
    };
  }

}
