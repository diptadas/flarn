package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.UserType;
import lombok.Data;

/**
 * {@link UserTypeUpdateRequest} class defines a request to update {@link UserType} of a particular user.
 * Used by admin to promote a user.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Data
public class UserTypeUpdateRequest {
    long id;
    UserType userType;
}
