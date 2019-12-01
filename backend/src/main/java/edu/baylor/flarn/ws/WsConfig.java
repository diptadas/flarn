package edu.baylor.flarn.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WsConfig class configures the endpoint for websocket connection and assigns a handler for WS operations.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {
    private final WsHandler wsHandler;

    public WsConfig(WsHandler wsHandler) {
        this.wsHandler = wsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, "/wsSession").setAllowedOrigins("*");
    }
}
