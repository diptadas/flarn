package edu.baylor.flarn.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link UpdatePasswordRequest} class defines a request for updating password.
 * Along with username and new password, it also contains the verification code sent to email.
 * Used in case user forgets password and wants to reset it.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePasswordRequest {
    private String username;
    private Integer confirmationCode;
    private String newPassword;
}
