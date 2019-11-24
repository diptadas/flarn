package edu.baylor.flarn.controllers;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.services.SessionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public Session createSession(@RequestBody Session session, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        return sessionService.createSession(session, user);
    }
}
