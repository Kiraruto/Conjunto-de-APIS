package io.github.kiraruto.conjuntoDeAPIS.model.users.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record SignUpRequest(@NotNull Long id,
                            @NotNull String username,
                            @NotNull String email,
                            @NotNull String password,
                            @NotNull UserRole userRole,
                            @NotNull Boolean active) {
    public static List<SignUpRequest> fromClimaList(List<User> saveGetUsers) {
        return saveGetUsers.stream()
                .map(u -> new SignUpRequest(u.getId(), u.getUsername(), u.getEmail(), u.getPassword(), u.getUserRole(), u.getActive()))
                .collect(Collectors.toList());
    }
}
