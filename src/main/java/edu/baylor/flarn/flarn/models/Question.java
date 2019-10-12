package edu.baylor.flarn.flarn.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String content;

    private List<String> options;

    private List<Integer> answer;

    @ManyToOne
    private ProblemSet problemSet;


}