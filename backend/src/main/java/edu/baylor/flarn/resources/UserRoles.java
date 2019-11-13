package edu.baylor.flarn.resources;

import com.google.common.collect.Lists;
import edu.baylor.flarn.models.UserType;

import java.util.List;

public class UserRoles {
    // create static fields to be used everywhere
    public static final String roleUser = "ROLE_USER";
    public static final String roleModerator = "ROLE_MODERATOR";
    public static final String roleAdmin = "ROLE_ADMIN";

    public static List<String> rolesForUserType(UserType userType) {
        // don't use Arrays.asList
        // we need mutable list to work with JPA
        // use google's collection API
        if (userType == UserType.ADMIN) {
            return Lists.newArrayList(roleUser, roleModerator, roleAdmin);
        } else if (userType == UserType.MODERATOR) {
            return Lists.newArrayList(roleUser, roleModerator);
        } else {
            return Lists.newArrayList(roleUser);
        }
    }
}
