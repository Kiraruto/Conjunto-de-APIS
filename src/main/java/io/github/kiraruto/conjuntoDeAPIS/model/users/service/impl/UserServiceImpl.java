package io.github.kiraruto.conjuntoDeAPIS.model.users.service.impl;

import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.repository.UserRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private static final ThreadLocal<String> currentUsername = new ThreadLocal<>();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            @Cacheable(value = "users", key = "#username")
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                currentUsername.set(username);

                return userRepository.findByEmail(username)
                        .orElseThrow(() -> {
                            return new UsernameNotFoundException("Usuário com e-mail " + username + " não encontrado.");
                        });
            }
        };
    }

    public String getCurrentUsername() {
        return currentUsername.get();
    }

    public void clear() {
        currentUsername.remove();
    }

    public Long returnIdUser() {
        Optional<User> user = userRepository.findByEmail(getCurrentUsername());

        if (user.get().getId() == null) {
            return null;
        }

        return user.get().getId();
    }
}
