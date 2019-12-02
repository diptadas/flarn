package edu.baylor.flarn.ws;

import com.google.gson.Gson;
import edu.baylor.flarn.exceptions.RecordNotFoundException;
import edu.baylor.flarn.models.Session;
import edu.baylor.flarn.resources.WsAuth;
import edu.baylor.flarn.security.JwtTokenProvider;
import edu.baylor.flarn.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * WsHandler class defines a handler for different WS operations.
 * It handles the activities when user attempts a problem.
 * <p>
 * Our server to client connections will be one to one, unlike typical chat applications.
 * So we don't need to save the list of sessions.
 * Instead we will always communicate to the client from which we have received a message.
 * We only need to map the sessionId with wsSessionId.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Component
@Slf4j
public class WsHandler extends TextWebSocketHandler {
    private final SessionService sessionService;
    private final JwtTokenProvider tokenProvider;

    public WsHandler(SessionService sessionService, JwtTokenProvider tokenProvider) {
        this.sessionService = sessionService;
        this.tokenProvider = tokenProvider;
    }

    private Map<String, Long> sessionMap = new HashMap<>();
    private Map<String, Boolean> authMap = new HashMap<>();

    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession wsSession, TextMessage message) {
        log.info("Received message from ws client:\n" + message.getPayload());

        // check if user is already authenticated
        // if not this must be an authentication request
        // or else close client's connection
        if (!authMap.containsKey(wsSession.getId())) {
            WsAuth auth = new Gson().fromJson(message.getPayload(), WsAuth.class);
            // validate token
            boolean res = tokenProvider.validateToken(auth.getToken());
            if(res) {
                authMap.put(wsSession.getId(), true);
                log.info("New User authenticated");
            } else {
                try {
                    wsSession.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        } else {
            // convert json text to session object
            Session session = new Gson().fromJson(message.getPayload(), Session.class);

            // set sessionID if ongoing session
            session.setId(sessionMap.getOrDefault(wsSession.getId(), null));

            // create or update the session
            try {
                session = sessionService.saveSessionForWsClient(session);
            } catch (RecordNotFoundException e) {
                log.info(e.getMessage());
            }

            // remember the associated sessionID for new session
            sessionMap.putIfAbsent(wsSession.getId(), session.getId());

            try {
                broadcastToSpecificClient(session.getId(), wsSession);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
    }

    // send sessionId to the same client
    private void broadcastToSpecificClient(Long sessionId, WebSocketSession wsSession) throws IOException {
        TextMessage textMessage = new TextMessage(sessionId + "");
        log.info("Sending message to ws clients: " + textMessage.getPayload());
        wsSession.sendMessage(textMessage);
    }

    // nothing to do when client is connected
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Ws client connected");
    }

    // update user point and
    // log the activity when session is closed
    @Override
    public void afterConnectionClosed(WebSocketSession wsSession, CloseStatus status) {
        log.info("Ws client disconnected");

        try {
            sessionService.updateUserPointForWsClient(sessionMap.get(wsSession.getId()));
        } catch (RecordNotFoundException e) {
            log.info(e.getMessage());
        }

        sessionMap.remove(wsSession.getId());
        authMap.remove(wsSession.getId());
    }
}
