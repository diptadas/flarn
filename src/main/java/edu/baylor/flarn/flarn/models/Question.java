package edu.baylor.flarn.flarn.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    private List<String> options = new ArrayList<>();

    private List<Integer> answer = new ArrayList<>();

    @ManyToOne
    private ProblemSet problemSet;


}