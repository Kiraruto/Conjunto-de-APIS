package io.github.kiraruto.encurtadorurl.domain.users.repository;

import io.github.kiraruto.encurtadorurl.domain.users.User;
import io.github.kiraruto.encurtadorurl.domain.users.role.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    User findByRole(UserRole role);

}
