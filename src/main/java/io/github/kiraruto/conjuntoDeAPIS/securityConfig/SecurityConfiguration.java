package io.github.kiraruto.conjuntoDeAPIS.securityConfig;

import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import io.github.kiraruto.conjuntoDeAPIS.securityConfig.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserService userService;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/auth/admin").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/auth/user").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/auth/user/put/{id}/desactive").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/auth/user/put/{id}/active").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/auth/user/getAll").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
