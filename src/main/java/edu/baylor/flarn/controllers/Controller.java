package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.ProblemSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {
  @GetMapping
  public String handshake() {
    return "Flarn Service is alive and eating potatoes!";
  }
}
