package io.github.kiraruto.conjuntoDeAPIS.model.users;

import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.DTOTransform;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "usuario")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    private Boolean active;

    public User(Long id, String username, String email, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public void atualizarUserInAdmin(DTOTransform dtoTransform) {
        if (dtoTransform.email() != null) {
            this.email = dtoTransform.email();
        }

        this.role = UserRole.ADMIN;
    }

    public void atualizarAdminInUser(DTOTransform dtoTransform) {
        if (dtoTransform.email() != null) {
            this.email = dtoTransform.email();
        }

        this.role = UserRole.USER;
    }

    public void atualizarActiveTrueToFalse(User user) {
        if (user.getActive() != null) {
            this.id = user.id;
        }

        this.active = false;
    }

    public void atualizarActiveFalseToTrue(User user) {
        if (user.getActive() != null) {
            this.id = user.id;
        }

        this.active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return role;
    }

    public void setUserRole(UserRole userRole) {
        this.role = userRole;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }
}
