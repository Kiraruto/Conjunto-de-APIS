package io.github.kiraruto.conjuntoDeAPIS.model.users.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    User findByRole(UserRole role);

    boolean existsByEmail(String email);
}
