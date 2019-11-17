package edu.baylor.flarn.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmUserRequest {
    private String username;
    private Integer confirmationCode;
}
