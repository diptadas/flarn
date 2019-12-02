package edu.baylor.flarn.resources;

import lombok.Data;

@Data
public class WsAuth {
    String token;
    Long userId;
}
