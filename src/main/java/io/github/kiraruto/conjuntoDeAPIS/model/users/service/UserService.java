package io.github.kiraruto.conjuntoDeAPIS.model.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
}
