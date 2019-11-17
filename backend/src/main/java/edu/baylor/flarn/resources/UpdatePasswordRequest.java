package edu.baylor.flarn.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePasswordRequest {
    private String username;
    private Integer confirmationCode;
    private String newPassword;
}
