package edu.baylor.flarn.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link ConfirmUserRequest} class defines the user email confirmation after registration.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmUserRequest {
    private String username;
    private Integer confirmationCode;
}
