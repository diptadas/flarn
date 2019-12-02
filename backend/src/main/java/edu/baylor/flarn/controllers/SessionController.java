package edu.baylor.flarn.controllers;

import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.services.SessionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public Session getSession(@PathVariable long id) throws RecordNotFoundException {
        return sessionService.findById(id);
    }

    @PostMapping
    public Long createSession(@RequestBody Session session, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        return sessionService.createSession(session, user).getId();
    }

    @GetMapping("/problems")
    public Long getSessionForProblem(@RequestParam("problemId") Long problemId, @AuthenticationPrincipal User user) throws RecordNotFoundException {
        return sessionService.getSessionIdForProblemAndUser(problemId, user.getId());
    }
}
