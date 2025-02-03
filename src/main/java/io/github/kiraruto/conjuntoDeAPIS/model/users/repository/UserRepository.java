package io.github.kiraruto.conjuntoDeAPIS.model.users.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    List<User> findByRole(UserRole role);

    boolean existsByEmail(String email);

    List<User> findAllByActiveIsTrue();
}
