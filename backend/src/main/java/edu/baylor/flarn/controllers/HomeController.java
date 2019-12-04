package edu.baylor.flarn.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@link HomeController} class defines REST endpoints for handshake.
 *
 * @author Dipta Das
 */

@RestController
public class HomeController {

    @GetMapping("/")
    public String handshake() {
        return "Welcome to Flarn!";
    }
}
