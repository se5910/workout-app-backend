package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @Column(nullable = false, unique = true)
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "Please enter your full name")
    private String fullName;

    @Column(nullable = false)
    @NotBlank(message = "Password field is required")
    private String password;
    private boolean enabled;

    @Transient
    private String confirmPassword;
    private Date created_At;
    private Date updated_At;

    @PrePersist
    protected void onCreate() {this.created_At = new Date();}

    @PreUpdate
    protected void onUpdate() {this.updated_At = new Date();}

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, boolean enabled, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
    return fullName;
  }

    public void setFullName(String fullName) {
    this.fullName = fullName;
  }

    public String getConfirmPassword() {
    return confirmPassword;
  }

    public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

    public Date getCreated_At() {
    return created_At;
  }

    public void setCreated_At(Date created_At) {
    this.created_At = created_At;
  }

    public Date getUpdated_At() {
    return updated_At;
  }

    public void setUpdated_At(Date updated_At) {
    this.updated_At = updated_At;
  }

    /* UserDetails interface methods */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
    return true;
  }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
    return true;
  }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
    return true;
  }

    @Override
    @JsonIgnore
    public boolean isEnabled() { return true; }
}

