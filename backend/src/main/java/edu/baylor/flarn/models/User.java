package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.baylor.flarn.resources.Unique;
import edu.baylor.flarn.resources.UserRoles;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * User class defines a user of the platform.
 * A User is associated with a UserType that indicates his permissions on the system.
 * A User can be association with other users using subscriptions.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "f_users")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Email(message = "username.required")
    @NotNull(message = "username.required")
    @Column(unique = true)
    @Unique(message = "username.already_exists")
    // username must be a valid email
    private String username;

    // by default null
    // users/sendConfirmationCode generates new code
    // after match set to null
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer confirmationCode;

    private boolean enabled;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String fullName;
    private String phoneNumber;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String biography;
    @URL
    private String avatarLink = "https://i.ibb.co/kgLYxgp/default-profile.png";
    @URL
    private String dpLink = "https://i.ibb.co/kgLYxgp/default-profile.png"; // display picture

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private UserType userType;

    // points based on attempted problems or, likes/dislikes of created problems
    // it will recalculated when a session is created or, when a review is crated
    private long points = 0;

    // list of other users this user is following
    @ManyToMany
    @JoinTable(
            name = "user_subscription",
            joinColumns = @JoinColumn(name = "subscription_ID"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_ID"))
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<User> subscriptions = new HashSet<>();

    // list of other users who follow this user i.e. followers
    @ManyToMany(mappedBy = "subscriptions")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<User> subscribers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Review> createdReviews = new HashSet<>();

    @OneToMany(mappedBy = "moderator")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Problem> createdProblems = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Session> participatedSessions = new HashSet<>();

    public User(@Email @NotNull String username, @NotNull String password, String fullName, String phoneNumber,
                String street, String city, String state, String zip, String biography, String avatarLink, String dpLink,
                UserType userType) {

        if (userType == null) {
            userType = UserType.LEARNER;
        }

        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.biography = biography;
        this.avatarLink = avatarLink;
        this.dpLink = dpLink;

        this.userType = userType;
        this.roles = UserRoles.rolesForUserType(userType);

        this.setEnabled(false); // disable user until email verification
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void subscribe(User user) { // helper to ensures bidirectional insert
        this.getSubscriptions().add(user);
        user.getSubscribers().add(this);
    }

    public void unsubscribe(User user) { // helper to ensures bidirectional insert
        this.getSubscriptions().remove(user);
        user.getSubscribers().remove(this);
    }
}
