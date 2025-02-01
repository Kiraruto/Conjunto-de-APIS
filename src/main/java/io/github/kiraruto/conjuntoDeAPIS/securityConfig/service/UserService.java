package io.github.kiraruto.conjuntoDeAPIS.securityConfig.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
}
