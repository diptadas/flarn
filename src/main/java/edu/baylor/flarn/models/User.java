package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Email
    @NotNull
    @Column(unique = true)
    private String username;


    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String fullname;
    private String phoneNumber;
    private String street;
    private String city;
    private String state;
    private String zip;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @NotNull
    private UserType userType = UserType.LEARNER;

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

    @ManyToMany(mappedBy = "subscriptions")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<User> subscribedUsers = new HashSet<>();

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
    private Set<ProblemSet> createdProblemSets = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Session> participatedSessions = new HashSet<>();

    public User(@Email @NotNull String username, @NotNull String password, String fullname, String phoneNumber,
                String street, String city, String state, String zip, List<String> roles) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.roles = roles;
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
        return true;
    }

    public void subscribe(User user) { // helper to ensures bidirectional insert
        this.getSubscriptions().add(user);
        user.getSubscribedUsers().add(this);
    }
}
