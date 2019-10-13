package edu.baylor.flarn;

import edu.baylor.flarn.models.Difficulty;
import edu.baylor.flarn.models.KnowledgeSource;
import edu.baylor.flarn.models.Option;
import edu.baylor.flarn.models.ProblemSet;
import edu.baylor.flarn.models.Question;
import edu.baylor.flarn.repositories.KnowledgeSourceRepository;
import edu.baylor.flarn.repositories.ProblemSetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FlarnApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlarnApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(ProblemSetRepository problemSetRepository, KnowledgeSourceRepository knowledgeSourceRepository) {
    return (args) -> {
      System.out.println("Initializing data");

      // save some knowledge sources
      KnowledgeSource knowledgeSource = new KnowledgeSource();
      knowledgeSource.setContentLink("http://google.com");
      knowledgeSourceRepository.save(knowledgeSource);

      // save a couple of problem sets
      ProblemSet problemSet1 = new ProblemSet();
      problemSet1.setKnowledgeSource(knowledgeSource);
      problemSet1.setTitle("Problem Set 1");
      problemSet1.setDescription("Here we are eating potatoes!");

      // create Questions
      Question question1 = new Question();
      question1.setContent("How are you doing?");
      List<Option> options = new ArrayList<Option>() {
        {
          new Option("fine");
          new Option("tired");
          new Option("hungry");
        }
      };
      question1.setOptions(options);

      problemSet1.setDifficulty(Difficulty.EASY);
      problemSetRepository.save(problemSet1);
    };
  }

}
