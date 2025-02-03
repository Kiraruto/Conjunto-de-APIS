package io.github.kiraruto.conjuntoDeAPIS.model.users;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.RoteiroDeViagens;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.DTOTransform;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.UpdateUser;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import io.github.kiraruto.conjuntoDeAPIS.securityConfig.SecurityConfiguration;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<UrlEncurtado> urlsEncurtadas;

    @OneToMany(mappedBy = "user")
    private List<ApiClima> climas;

    @OneToMany(mappedBy = "user")
    private List<RoteiroDeViagens> roteiro;

    public User(Long id, String username, String email, String password, UserRole role, Boolean active, List<UrlEncurtado> urlsEncurtadas, List<ApiClima> climas) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.urlsEncurtadas = urlsEncurtadas;
        this.climas = climas;
    }

    public User() {
    }

    public User(Long saveId) {
        addUrlEncurtada(new UrlEncurtado(saveId));
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

    public void addUrlEncurtada(UrlEncurtado urlEncurtado) {
        urlEncurtado.setUser(this);
        urlsEncurtadas.add(urlEncurtado);
    }

    public void addClima(ApiClima apiClima) {
        apiClima.setUser(this);
        climas.add(apiClima);
    }

    public void addRoteiro(RoteiroDeViagens roteiroDeViagens) {
        roteiroDeViagens.setUser(this);
        roteiro.add(roteiroDeViagens);
    }

    public void atualizarUsuario(UpdateUser signUpRequest) {

        if (signUpRequest.username() != null) {
            this.username = signUpRequest.username();
        }

        if (signUpRequest.email() != null) {
            this.email = signUpRequest.email();
        }

        if (signUpRequest.password() != null) {
            this.password = new BCryptPasswordEncoder().encode(signUpRequest.password());
        }

        if (signUpRequest.userRole() != null) {
            this.role = signUpRequest.userRole();
        }

        if (signUpRequest.active() != null) {
            this.active = signUpRequest.active();
        }
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
