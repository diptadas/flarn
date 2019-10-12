package edu.baylor.flarn.flarn.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
public class User extends org.springframework.security.core.userdetails.User {
    @NotNull
    private UserType userType;

    public User(UserType userType, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userType = userType;
    }
}
