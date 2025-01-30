package io.github.kiraruto.encurtadorurl.domain.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
}
