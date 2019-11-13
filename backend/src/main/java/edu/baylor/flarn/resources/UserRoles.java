package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.UserType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRoles {
    // create static fields to be used everywhere
    public static final String roleUser = "ROLE_USER";
    public static final String roleModerator = "ROLE_MODERATOR";
    public static final String roleAdmin = "ROLE_ADMIN";

    public static List<String> adminRoles = Arrays.asList(roleUser, roleModerator, roleAdmin);
    public static List<String> moderatorRoles = Arrays.asList(roleUser, roleModerator);
    public static List<String> learnerRoles = Collections.singletonList(roleUser);

    public static List<String> rolesForUserType(UserType userType) {
        if (userType == UserType.ADMIN) {
            return adminRoles;
        } else if (userType == UserType.MODERATOR) {
            return moderatorRoles;
        } else {
            return learnerRoles;
        }
    }
}
