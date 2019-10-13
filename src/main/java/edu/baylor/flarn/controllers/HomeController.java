package edu.baylor.flarn.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {

  @GetMapping("/")
  public String handshake() {
    return "Welcome to Flarn!";
  }
}
