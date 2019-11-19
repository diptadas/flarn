package edu.baylor.flarn.services;

import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.models.User;
import edu.baylor.flarn.repositories.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(Session session, User user) {
        if (session.getUser() == null) { // set current logged in user
            session.setUser(user);
        }
        return sessionRepository.save(session);
    }
}
