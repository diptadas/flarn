package edu.baylor.flarn.flarn.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotNull
    private String name;

    @OneToMany
    private ProblemSet problemSet;
}
