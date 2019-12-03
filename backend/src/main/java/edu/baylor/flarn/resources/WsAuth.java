package edu.baylor.flarn.resources;

import lombok.Data;

/**
 * {@link WsAuth} class wraps the userId with authentication token for a websocket connection.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Data
public class WsAuth {
    String token;
    Long userId;
}
