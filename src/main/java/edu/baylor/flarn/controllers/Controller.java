package edu.baylor.flarn.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {
    @GetMapping
    public String handshake() {
        return "Flarn Service is alive and eating potatoes!";
    }
}
